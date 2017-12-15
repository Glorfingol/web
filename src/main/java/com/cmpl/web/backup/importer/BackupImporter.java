package com.cmpl.web.backup.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmpl.web.core.backup.reader.CSVReader;

public class BackupImporter {

  private static final Logger LOGGER = LoggerFactory.getLogger(BackupImporter.class);

  private final CSVReader csvReader;
  private final String backupFilePath;

  public BackupImporter(CSVReader csvReader, String backupFilePath) {
    this.csvReader = csvReader;
    this.backupFilePath = backupFilePath;
  }

  public void importBackup() {

    LOGGER.info("Debut de l'import des donnees");
    boolean hasData = false;
    try {
      hasData = unzipLatestIfExists();
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la decompression du backup ", e);
    }
    if (!hasData) {
      LOGGER.info("Rien a importer");
      return;
    }
    LOGGER.info("Lecture des fichiers et import");
    try {
      readCSVFilesAndImportData();
    } catch (Exception e) {
      LOGGER.error("Echec de l'import des donnees venant des fichiers csv");
    }

    LOGGER.info("Suppression des fichiers desarchives");
    deleteUnzippedFiles();

    LOGGER.info("Suppression des fichiers plus vieux que dix jours");
    deleteOlderThanTenDaysFiles();

    LOGGER.info("Fin de l'import des donnees");

  }

  private boolean unzipLatestIfExists() throws IOException {

    File backupFolder = new File(backupFilePath);
    LOGGER.info("Dossier de backup : " + backupFolder.getAbsolutePath());
    if (backupFolder.exists()) {
      File[] files = backupFolder.listFiles(file -> file.isFile());
      File latestBackup = computeLatestBackup(files);
      if (latestBackup != null) {
        LOGGER.info("Dernier backup : " + latestBackup);
        unzipBackup(latestBackup);
        return true;
      }

    }
    LOGGER.info("Dossier de backup inexistant ou pas de zip" + backupFolder.getAbsolutePath());
    return false;
  }

  private void unzipBackup(File latestBackup) throws FileNotFoundException, IOException {
    ZipInputStream zis = new ZipInputStream(new FileInputStream(latestBackup));
    ZipEntry ze = zis.getNextEntry();
    byte[] buffer = new byte[1024];
    while (ze != null) {
      String fileName = ze.getName();
      File newFile = new File(backupFilePath + File.separator + fileName);

      FileOutputStream fos = new FileOutputStream(newFile);

      int len;
      while ((len = zis.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }

      fos.close();
      ze = zis.getNextEntry();
    }

    zis.closeEntry();
    zis.close();
  }

  private File computeLatestBackup(File[] files) {
    File latestBackup = null;
    long lastMod = Long.MIN_VALUE;
    for (File file : files) {
      if (file.lastModified() > lastMod) {
        latestBackup = file;
        lastMod = file.lastModified();
      }
    }
    return latestBackup;
  }

  private void readCSVFilesAndImportData() {
    csvReader.extractAllDataFromCSVFiles();
  }

  private void deleteUnzippedFiles() {
    List<File> filesToDelete = getCSVFiles();
    filesToDelete.stream().forEach(file -> file.delete());
  }

  private void deleteOlderThanTenDaysFiles() {
    File directory = new File(backupFilePath);
    if (!directory.exists()) {
      return;
    }
    List<File> filesInBackup = Arrays.asList(directory.listFiles());
    filesInBackup.stream().forEach(file -> deleteFileIfOlderThanTenDays(file));

  }

  private void deleteFileIfOlderThanTenDays(File fileToExamine) {
    long diff = new Date().getTime() - fileToExamine.lastModified();

    long tenDaysInMilliseconds = 10 * 24 * 60 * 60 * 1000;
    if (diff > tenDaysInMilliseconds) {
      fileToExamine.delete();
    }
  }

  private List<File> getCSVFiles() {
    List<File> csvFiles = new ArrayList<>();
    File directory = new File(backupFilePath);
    if (!directory.exists()) {
      return csvFiles;
    }
    csvFiles = Arrays.asList(directory.listFiles((dir, name) -> name.endsWith(".csv")));
    return csvFiles;
  }

}
