package controller;

import java.util.ArrayList;
import java.sql.Date;

import model.Order;
import model.Receipt;

public class ReceiptController
{

	public ReceiptController()
	{
		// TODO Auto-generated constructor stub
	}

	public static void createReceipt(Order order, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		if(order == null) return;
		if(receiptPaymentType.isBlank()) return;
		if(receiptPaymentAmount <= 0.0) return;
		if(receiptPaymentDate == null) return;
		Receipt.createReceipt(order, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
	}
	
	public static void updateReceipt(int orderId, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		if(orderId <= 0) return;
		if(receiptPaymentType.isBlank()) return;
		if(receiptPaymentAmount <= 0.0) return;
		if(receiptPaymentDate == null) return;
		Receipt.updateReceipt(orderId, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
	}
	
	public static void deleteReceipt(int orderId)
	{
		if(orderId <= 0) return;
		Receipt.deleteReceipt(orderId);
	}
	
	public static Receipt getReceiptById(int receiptId)
	{
		return Receipt.getReceiptById(receiptId);
	}
	
	public static ArrayList<Receipt> getAllReceipts()
	{
		return Receipt.getAllReceipts();
	}
	
	
}
