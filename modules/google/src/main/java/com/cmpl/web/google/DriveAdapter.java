package com.cmpl.web.google;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import java.io.IOException;

public interface DriveAdapter {

  void sendFilesToGoogleDrive(File fileToCreate, InputStreamContent input) throws IOException;

}
