package com.cmpl.web.google;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.util.Objects;

public class DefaultDriveAdapter implements DriveAdapter {

  private final Drive driveService;

  public DefaultDriveAdapter(Drive driveService) {

    this.driveService = Objects.requireNonNull(driveService);

  }

  @Override
  public void sendFilesToGoogleDrive(File fileToCreate, InputStreamContent input)
      throws IOException {
    driveService.files().create(fileToCreate, input).execute();
  }

}
