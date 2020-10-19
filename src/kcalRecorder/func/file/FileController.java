package kcalRecorder.func.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import kcalRecorder.model.vo.Meal;

public class FileController {
	private String filePath;
	private String fileName;
	
	public FileController(String filePath,String fileName) {
		this.fileName = fileName;
		this.filePath = filePath;
	}
	public boolean saveFile(ArrayList<Meal> list) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean check = true;
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
			check = !check;
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Meal> readFile(){
		ArrayList<Meal> list= null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			list = (ArrayList<Meal>)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
