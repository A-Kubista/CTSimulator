/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctsimulator;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.ImageToDicom;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;

/**
 * generowanie i zapisywanie pliku DICOM
 * @author LU
 */
public class DICOMGenerator {
    
    /**
     * 
     * @param simulation obiekt CT, z którego zostaną pobrane obrazy do zapisu
     * @param name imię i nazwisko pacjenta
     * @param pesel pesel pacjenta
     * @param date data wykonania badania w formacie "yyyy-mm-dd-hh-mm"
     */
    public static void DICOMGenerator(CT simulation, String name, String pesel, String date){
        // save jpg image
        try {
    // retrieve image
            BufferedImage bi = simulation.getFilteredResultImg();
            File outputfile = new File("contrast.jpg");
            ImageIO.write(bi, "png", outputfile);
            } catch (IOException e) {

            }
        
            String scJpegFilePath = "contrast.jpg";
		String newDicomFile = "contrast.dcm";
		try {
        	//generate the DICOM file from the jpeg file and the other attributes supplied
			new ImageToDicom(scJpegFilePath, //path to existing JPEG image 
					         newDicomFile, //output DICOM file with full path
					         name, //name of patient
					         pesel, //patient id
					         "id badania", //study id
					         "seria", //series number
					         "instacja"); //instance number
		    //now, dump the contents of the DICOM file to the console
			AttributeList list = new AttributeList();
    		list.read(newDicomFile);
    		System.out.println(list.toString());
        
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
