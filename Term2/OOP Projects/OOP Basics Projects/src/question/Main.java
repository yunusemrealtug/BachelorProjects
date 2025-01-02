
package question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {


	public static void main(String args[]) {

		Customer[] customers;
		Operator[] operators;

		int C, O, N;

		File inFile = new File(args[0]);  // args[0] is the input file
		File outFile = new File(args[1]);  // args[1] is the output file
		try {
			PrintStream outstream = new PrintStream(outFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Scanner reader;
		try {
			reader = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find input file");
			return;
		}

		C = reader.nextInt();
		O = reader.nextInt();
		N = reader.nextInt();

		customers = new Customer[C];
		operators = new Operator[O];

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
		PrintStream outstream1;
		try {
			outstream1 = new PrintStream(outFile);
		}catch(FileNotFoundException e2) {
			e2.printStackTrace();
			return;
		}
		
		
		
		for (int i = 0; i < N; i++) {
			int number=reader.nextInt();
			
			if (number==2) {
				for (int a=0; a<O; a++) {
					if (operators[a]==null) {
						Operator a1 =  new Operator (a, reader.nextDouble(), reader.nextDouble(), reader.nextDouble(), reader.nextInt());
						operators [a]=a1;
						break;
					}
				}
			}
			else if (number==1) {
				for (int b=0; b<C; b++) {
					if (customers[b]==null) {
						Customer b1 =  new Customer (b, reader.next(), reader.nextInt(), operators[reader.nextInt()], reader.nextDouble());
						customers [b]=b1;
						break;
					}
				}
			}
			else if (number==3) {
				int c1=reader.nextInt();
				int c2=reader.nextInt();
				int c3=reader.nextInt();
				if (c1!=c2) {
					if (customers [c1].getBill().check(customers [c1].getOperator().calculateTalkingCost(c3, customers [c1]))) {
						customers [c1].getBill().add(customers [c1].getOperator().calculateTalkingCost(c3, customers [c1]));
						customers [c1].talk(c3, customers [c2]);
					}
					
				}
			}
			else if (number==4) {
				int d1=reader.nextInt();
				int d2=reader.nextInt();
				int d3=reader.nextInt();
				if (d1!=d2) {
					if (customers [d1].getBill().check(customers [d1].getOperator().calculateMessageCost(d3, customers [d1], customers [d2]))) {
						customers [d1].getBill().add(customers [d1].getOperator().calculateMessageCost(d3, customers [d1], customers [d2]));
						customers [d1].message(d3, customers [d2]);
					}
						
				}
			}
			else if (number==5) {
				int e1=reader.nextInt();
				double e2=reader.nextDouble();
				if (customers [e1].getBill().check(customers [e1].getOperator().calculateNetworkCost(e2))) {
					customers [e1].getBill().add(customers [e1].getOperator().calculateNetworkCost(e2));
					customers [e1].connection(e2);
				}
			}
			else if (number==6) {
				int f1=reader.nextInt();
				double f2=reader.nextDouble();
				customers [f1].getBill().pay(f2);
			}
			else if (number==7) {
				int g1=reader.nextInt();
				int g2=reader.nextInt();
				customers [g1].setOperator(operators [g2]);
			}
			else if (number==8) {
				int h1=reader.nextInt();
				double h2=reader.nextDouble();
				customers [h1].getBill().changeTheLimit(h2);
			}
		}
		int talkSorting=0;
		String mostTalking=customers[0].getName();
		int messageSorting=0;
		String mostMessage=customers[0].getName();
		double netSorting=0;
		String mostNetwork=customers[0].getName();
		
		for (int i = 0; i < O; i++) {
			outstream1.print("Operator ");
			outstream1.print(i);
			outstream1.print(" : ");
			outstream1.print(operators[i].getTalkingTime());
			outstream1.print(" ");
			outstream1.print(operators[i].getMessageQuantity());
			outstream1.print(" ");
			outstream1.printf("%.2f", operators[i].getNetworkUsed());
			outstream1.print("\n");
			}
		
		for (int i = 0; i < C; i++) {
			outstream1.print("Customer ");
			outstream1.print(i);
			outstream1.print(" : ");
			outstream1.printf("%.2f", customers [i].getBill().getPayments());
			outstream1.print(" ");
			outstream1.printf("%.2f", customers [i].getBill().getCurrentDebt());
			outstream1.print("\n");
			}
		
		for (int i=0; i<C; i++) {
			if (i==0) {
				talkSorting=customers[i].getTalkingTime();
			}
			else {
				if (customers[i].getTalkingTime()>talkSorting) {
					talkSorting=customers[i].getTalkingTime();
					mostTalking=customers[i].getName();
				}
			}
		}
		for (int i=0; i<C; i++) {
			if (i==0) {
				messageSorting=customers[i].getMessageQuantity();
			}
			else {
				if (customers[i].getMessageQuantity()>messageSorting) {
					messageSorting=customers[i].getMessageQuantity();
					mostMessage=customers[i].getName();
				}
			}
		}
		for (int i=0; i<C; i++) {
			if (i==0) {
				netSorting=customers[i].getNetworkUsed();
			}
			else {
				if (customers[i].getNetworkUsed()>netSorting) {
					netSorting=customers[i].getNetworkUsed();
					mostNetwork=customers[i].getName();
				}
			}
		}
		outstream1.print(mostTalking);
		outstream1.print(" : ");
		outstream1.print(talkSorting);
		outstream1.print("\n");
		outstream1.print(mostMessage);
		outstream1.print(" : ");
		outstream1.print(messageSorting);
		outstream1.print("\n");
		outstream1.print(mostNetwork);
		outstream1.print(" : ");
		outstream1.printf("%.2f", netSorting);
		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
	} 
}

