
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import ports.Port;
import ships.Ship;
/**
 * main class of the project
 * @author Yunus Emre
 *
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		// 
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		final ArrayList<Port> allports=new ArrayList<Port>();
		final ArrayList<Ship> allships=new ArrayList<Ship>();
		final ArrayList<Container> liquidContainers=new ArrayList<Container>();
		final ArrayList<Container> refrigeratedContainers=new ArrayList<Container>();
		final ArrayList<Container> heavyContainers=new ArrayList<Container>();
		final ArrayList<Container> basicContainers=new ArrayList<Container>();
		final ArrayList<Container> allcontainers=new ArrayList<Container>();
		
		final int N = in.nextInt();
		for (int i = 0; i < N; i++) {
			int number=in.nextInt();
			if (number==3) {
				Port p1= new Port(allports.size(), in.nextDouble(), in.nextDouble());
				allports.add(p1);
			}
			if (number==2) {
				Ship s1= new Ship(allships.size(), allports.get(in.nextInt()), in.nextInt(), 
				in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextDouble());
				allships.add(s1);
				
			}
			if (number==1) {
				int portNumber=in.nextInt();
				int weight=in.nextInt();
				String word=in.nextLine();
				if (word.contains("L")) {
					Container lc1=new LiquidContainer(allcontainers.size(), weight);
					liquidContainers.add(lc1);
					allcontainers.add(lc1);
					allports.get(portNumber).getContainers().add(lc1);
					}
				else if (word.contains("R")) {
					Container rc1=new RefrigeratedContainer(allcontainers.size(), weight);
					refrigeratedContainers.add(rc1);
					allcontainers.add(rc1);
					allports.get(portNumber).getContainers().add(rc1);
				}
				 
				else {
				     if (weight>3000) {
				    	 Container hc1=new HeavyContainer(allcontainers.size(), weight);
							heavyContainers.add(hc1);
							allcontainers.add(hc1);
							allports.get(portNumber).getContainers().add(hc1);
				     }
				     else {
				    	 Container bc1=new BasicContainer(allcontainers.size(), weight);
							basicContainers.add(bc1);
							allcontainers.add(bc1);
							allports.get(portNumber).getContainers().add(bc1);
				     }
				}
			}
			if (number==4) {
				allships.get(in.nextInt()).load(allcontainers.get(in.nextInt()));
			}
			if (number==5) {
				allships.get(in.nextInt()).unLoad(allcontainers.get(in.nextInt()));
			}
			if (number==6) {
				int shipNumber=in.nextInt();
				int portNumber=in.nextInt();
				allships.get(shipNumber).sailTo(allports.get(portNumber));
			}
			if (number==7) {
				allships.get(in.nextInt()).reFuel(in.nextDouble());
			}
		}
		
		for (int i = 0; i < allports.size(); i++) {
			out.print("Port ");
			out.print(i);
			out.print(": (");
			out.printf("%.2f", allports.get(i).getX());
			out.print(", ");
			out.printf("%.2f", allports.get(i).getY());
			out.println(")");
			if (allports.get(i).containerCheck("containers.BasicContainer")) {
				out.print("  BasicContainer:");
				for (int k=0; k<allports.get(i).getContainers().size(); k++) {
					if (allports.get(i).getContainers().get(k).getClass().getName()=="containers.BasicContainer") {
						out.print(" ");
						out.print(allports.get(i).getContainers().get(k).getID());
					}
				}
				out.print("\n");
			}
			if (allports.get(i).containerCheck("containers.HeavyContainer")) {
				out.print("  HeavyContainer:");
				for (int k=0; k<allports.get(i).getContainers().size(); k++) {
					if (allports.get(i).getContainers().get(k).getClass().getName()=="containers.HeavyContainer") {
						out.print(" ");
						out.print(allports.get(i).getContainers().get(k).getID());
					}
				}
				out.print("\n");
			}
			
			if (allports.get(i).containerCheck("containers.RefrigeratedContainer")) {
				out.print("  RefrigeratedContainer:");
				for (int k=0; k<allports.get(i).getContainers().size(); k++) {
					if (allports.get(i).getContainers().get(k).getClass().getName()=="containers.RefrigeratedContainer") {
						out.print(" ");
						out.print(allports.get(i).getContainers().get(k).getID());
					}
				}
				out.print("\n");
			}
			
			if (allports.get(i).containerCheck("containers.LiquidContainer")) {
				out.print("  LiquidContainer:");
				for (int k=0; k<allports.get(i).getContainers().size(); k++) {
					if (allports.get(i).getContainers().get(k).getClass().getName()=="containers.LiquidContainer") {
						out.print(" ");
						out.print(allports.get(i).getContainers().get(k).getID());
					}
				}
				out.print("\n");
			}
			if (allports.get(i).getCurrent().size()>0) {
				for (int k=0; k<allports.get(i).getCurrent().size(); k++) {
					out.print("  Ship ");
					out.print(allports.get(i).getCurrent().get(k).getID());
					out.print(": ");
					out.printf("%.2f", allports.get(i).getCurrent().get(k).getFuel());
					out.print("\n");
					if (allports.get(i).getCurrent().get(k).containerCheck("containers.BasicContainer")) {
						out.print("    BasicContainer:");
						for (int l=0; l<allports.get(i).getCurrent().get(k).getCurrentContainers().size(); l++) {
							if (allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getClass().getName()=="containers.BasicContainer") {
								out.print(" ");
								out.print(allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getID());
							}
						}
						out.print("\n");
					}
					if (allports.get(i).getCurrent().get(k).containerCheck("containers.HeavyContainer")) {
						out.print("    HeavyContainer:");
						for (int l=0; l<allports.get(i).getCurrent().get(k).getCurrentContainers().size(); l++) {
							if (allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getClass().getName()=="containers.HeavyContainer") {
								out.print(" ");
								out.print(allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getID());
							}
						}
						out.print("\n");
					}
					if (allports.get(i).getCurrent().get(k).containerCheck("containers.RefrigeratedContainer")) {
						out.print("    RefrigeratedContainer:");
						for (int l=0; l<allports.get(i).getCurrent().get(k).getCurrentContainers().size(); l++) {
							if (allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getClass().getName()=="containers.RefrigeratedContainer") {
								out.print(" ");
								out.print(allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getID());
							}
						}
						out.print("\n");
					}
					if (allports.get(i).getCurrent().get(k).containerCheck("containers.LiquidContainer")) {
						out.print("    LiquidContainer:");
						for (int l=0; l<allports.get(i).getCurrent().get(k).getCurrentContainers().size(); l++) {
							if (allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getClass().getName()=="containers.LiquidContainer") {
								out.print(" ");
								out.print(allports.get(i).getCurrent().get(k).getCurrentContainers().get(l).getID());
							}
						}
						out.print("\n");
					}
				}
			}
		}
		
		
		in.close();
		out.close();
	}
		
		
		
		
		
		
}




//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

