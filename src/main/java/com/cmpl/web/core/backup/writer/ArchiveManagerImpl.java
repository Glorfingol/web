package com.cmpl.web.core.backup.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;

public class ArchiveManagerImpl implements ArchiveManager {

  private final String backupFilePath;
  private final DateTimeFormatter dateTimeFormatter;
  private final Drive driveService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveManagerImpl.class);

  public ArchiveManagerImpl(String backupFilePath, Drive driveService) {
    this.backupFilePath = backupFilePath;
    this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    this.driveService = driveService;
  }

  @Override
  public void archiveData() {

    LOGGER.info("Recuperation des fichiers a archiver");
    List<File> filesToZip = getCSVFiles();
    LOGGER.info("Archivage des fichiers");
    File zipFile = zipFiles(filesToZip);
    LOGGER.info("Suppression des fichiers CSV");
    deleteCSVFiles(filesToZip);
    if (zipFile != null && zipFile.exists()) {
      copyZipToGoogleDrive(zipFile);

    }
    LOGGER.info("Suppression des backup de plus de 10 jours");
    deleteOlderThanTenDaysFiles();

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

  private File zipFiles(List<File> filesToZip) {
    File directory = new File(backupFilePath);

    if (directory.exists()) {
      try {
        String zipFile = backupFilePath + File.separator + "backup_web_"
            + LocalDateTime.now().format(dateTimeFormatter) + ".zip";
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        filesToZip.stream().forEach(file -> zipFile(zos, file));
        zos.closeEntry();
        zos.close();
        return new File(zipFile);
      } catch (Exception e) {
        LOGGER.info("Erreur lors de l'archivage des fichiers CSV", e);
      }

    }
    return null;

  }

  private void zipFile(ZipOutputStream zos, File fileToZip) {
    try {
      byte[] buffer = new byte[1024];
      ZipEntry ze = new ZipEntry(fileToZip.getName());
      zos.putNextEntry(ze);
      FileInputStream in = new FileInputStream(fileToZip);
      int len;
      while ((len = in.read(buffer)) > 0) {
        zos.write(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      LOGGER.info("Erreur lors de l'archivage du fichiers CSV " + fileToZip.getName(), e);

    }

  }

  private void deleteCSVFiles(List<File> filesToDelete) {
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

  private void copyZipToGoogleDrive(File zipFile) {
    try {
      com.google.api.services.drive.model.File fileToCreate = new com.google.api.services.drive.model.File();
      fileToCreate.setDescription("Backup file");
      fileToCreate.setMimeType("application/zip");
      fileToCreate.setName(zipFile.getName());
      InputStreamContent is = new InputStreamContent("*/*", new FileInputStream(zipFile));
      driveService.files().create(fileToCreate, is).execute();
    } catch (Exception e) {
      LOGGER.error("Echec de la creation dans le drive", e);
    }
  }
}
