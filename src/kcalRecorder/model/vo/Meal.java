package kcalRecorder.model.vo;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import autoComplete.trie.HangulType;
import autoComplete.trie.hangulTrie;

public class Meal implements Serializable{
	private int m_no;
	private ArrayList<Food> foodArr;
	private Date date;
	public Meal() {
		date = new Date();
	}
	public Meal(ArrayList<Food> list) {
		date = new Date();
		foodArr = new ArrayList<Food>();
		for(Food f : list) {
			foodArr.add(f);
		}
	}

	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public ArrayList<Food> getFoodArr() {
		return foodArr;
	}
	public void setFoodArr(ArrayList<Food> foodArr) {
		this.foodArr = foodArr;
	}
	public Date getDate() {
		return date;
	}
	public java.sql.Date getSqlDate() {
		return new java.sql.Date(date.getTime());
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setDate(java.sql.Date date) {
		this.date = new Date(date.getTime());
	}
	public Food findHighestTotalKcal() {
		int max = 0;
		Food tempToReturn = null;
		for(Food fd : foodArr) {
			if(fd.getTotalKcal()> max) {
				max = Integer.max(max, fd.getTotalKcal());
				tempToReturn = fd;
			}
		}
		return tempToReturn;
	}
	public boolean isFoodNameIncluded(String valueBeIncluded) {
		for(Food f : foodArr) {
			if(f.getName().contains(valueBeIncluded))
				return true;
		}
		return false;
	}
	public Food findLowestTotalKcal() {
		int min = Integer.MAX_VALUE;
		Food tempToReturn = null;
		for(Food fd : foodArr) {
			if(fd.getTotalKcal() < min) {
				min = Integer.max(min, fd.getTotalKcal());
				tempToReturn = fd;
			}
		}
		return tempToReturn;
	}
	public void makeDate(int year,int month,int day,int hour,int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.YEAR, year);
		calendar.set(calendar.MONTH, month);
		calendar.set(calendar.DAY_OF_MONTH, day);
		calendar.set(calendar.HOUR, hour);
		calendar.set(calendar.MINUTE, minute);
		date.setTime(calendar.getTimeInMillis());
	}
	public boolean isFoodChosungIncluded(String valueBeIncluded) {
		for(Food f : foodArr) {
			char[] arr = Normalizer.normalize(f.getName(), Normalizer.Form.NFD).toCharArray();
			StringBuilder curStr = new StringBuilder();
			char[] arr2 = valueBeIncluded.toCharArray();
			for(int i = 0 ; i < arr.length ; ++i) {
				if(HangulType.Chosung.isJamo(arr[i])) {
					curStr.append(arr[i]);
				}
			}
			char[] arr3  = curStr.toString().toCharArray();
			System.out.println(arr2);
			System.out.println(arr3);
			boolean check = true;
			for(int i = 0 ; i < arr2.length ; ++i) {
				if(HangulType.Other.setCompatibilityToChosung(arr2[i]) != arr3[i]) {
					check = false;
				}
			}
			System.out.println(check);
			return check;
		}
		return true;
	}
	
}
