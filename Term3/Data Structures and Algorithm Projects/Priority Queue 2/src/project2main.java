import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;
import java.util.PriorityQueue;


public class project2main {
	
	public static int numOfPlayers;
	public static int numOfArrivals;
	public static int numOfPhysios;
	public static int numOfCoaches;
	public static int numOfMasseurs;
	public static int maxTrainingLength;
	public static int maxMassageLength;
	public static int maxPhysiotherapyLength;
	public static double totalWaitingTraining;
	public static double totalWaitingMassage;
	public static double totalWaitingPhysiotherapy;
	public static int totalTrainingAmount;
	public static int totalMassageAmount;
	public static int totalPhysiotherapyAmount;
	public static double totalTrainingDuration;
	public static double totalMassageDuration;
	public static double totalPhysiotherapyDuration;
	public static double maxPhysiotherapyDuration;
	public static int maxPhysiotherapyPerson;
	public static double leastMassageDuration;
	public static int leastMassagePerson;
	public static double finishTime;
	public static int cancelledAttempts;
	public static int invalidAttempts;
	public static HashMap <Integer, Player> players;
	public static HashMap <Facility, Training> trainingMap;
	public static HashMap <Training, Ongoings> ongoingTrainingMap;
	public static HashMap <Facility, Massage> massageMap;
	public static HashMap <Massage, Ongoings> ongoingMassageMap;
	public static HashMap <Ongoings, Physiotherapy> physioMap;
	public static HashMap <Physiotherapy, Ongoings> ongoingPhysioMap;
	public static PriorityQueue <Training> trainingQueue;
	public static PriorityQueue <Massage> massageQueue;
	public static PriorityQueue <Physiotherapy> physiotherapyQueue;
	public static PriorityQueue <Facility> collectiveQueue;
	public static PriorityQueue <Physiotherapist> physiotherapistQueue;
	public static PriorityQueue <Ongoings> ongoingsQueue;
	public static HashMap <Ongoings, Physiotherapy> ingoingPhysioMap;
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		Locale.setDefault(new Locale("en", "US"));
		Scanner in = new Scanner(new File(args[0])).useLocale(Locale.US);
		PrintStream out = new PrintStream(new File(args[1]));
		players = new HashMap<Integer, Player>();
		trainingMap=new HashMap<Facility, Training>();
		ongoingTrainingMap=new HashMap<Training, Ongoings>();
		massageMap=new HashMap<Facility, Massage>();
		ongoingMassageMap=new HashMap<Massage, Ongoings>();
		physioMap=new HashMap<Ongoings, Physiotherapy>();
		ongoingPhysioMap=new HashMap<Physiotherapy, Ongoings>();
		ingoingPhysioMap=new HashMap<Ongoings, Physiotherapy>();
		trainingQueue=new PriorityQueue <Training>();
		massageQueue=new PriorityQueue <Massage>();
		physiotherapistQueue=new PriorityQueue <Physiotherapist>();
		physiotherapyQueue=new PriorityQueue <Physiotherapy>();
		collectiveQueue=new PriorityQueue <Facility>();
		ongoingsQueue=new PriorityQueue <Ongoings>();
		maxTrainingLength=0;
		maxMassageLength=0;
		maxPhysiotherapyLength=0;
		totalWaitingTraining=0;
		totalWaitingMassage=0;
		totalWaitingPhysiotherapy=0;
		totalTrainingAmount=0;
		totalMassageAmount=0;
		totalPhysiotherapyAmount=0;
		totalTrainingDuration=0;
		totalMassageDuration=0;
		totalPhysiotherapyDuration=0;
		maxPhysiotherapyDuration=-1;
		maxPhysiotherapyPerson=-1;
		leastMassageDuration=-1;
		leastMassagePerson=0;
		cancelledAttempts=0;
		invalidAttempts=0;
		finishTime=0;
		numOfPlayers=in.nextInt();
		
		
		for (int i=0; i<numOfPlayers; i++) {
			
			Player p1=new Player(in.nextInt(), in.nextInt());
			players.put(p1.getId(), p1);
		}
		
		numOfArrivals=in.nextInt();
		
		for (int i=0; i<numOfArrivals; i++) {
			
			if (in.next().equals("t")) {
				
				int playerId=in.nextInt();
				double trainingTime=in.nextDouble();
				double trainingDuration=in.nextDouble();
				Training t1=new Training(players.get(playerId), trainingTime, trainingDuration);
				Facility f1=new Facility(players.get(playerId), trainingTime, trainingDuration, "t");
				Physiotherapy p1=new Physiotherapy(t1);
				Ongoings o1=new Ongoings(players.get(playerId), t1.getLeavingTime(), "t");
				Ongoings o2=new Ongoings(players.get(playerId), p1.getLeavingTime(), "p");
				collectiveQueue.add(f1);
				trainingMap.put(f1, t1);
				ongoingTrainingMap.put(t1, o1);
				physioMap.put(o1,p1);
				ongoingPhysioMap.put(p1,o2);
				ingoingPhysioMap.put(o2, p1);
				
			}
			else {
				int playerId=in.nextInt();
				double trainingTime=in.nextDouble();
				double trainingDuration=in.nextDouble();
				Massage m1=new Massage(players.get(playerId), trainingTime, trainingDuration);
				Facility f1=new Facility(players.get(playerId), trainingTime, trainingDuration, "m");
				collectiveQueue.add(f1);
				massageMap.put(f1, m1);
				Ongoings o1=new Ongoings(players.get(playerId), m1.getLeavingTime(), "m");
				ongoingMassageMap.put(m1, o1);
				
			}
			 
		}
		
		
		numOfPhysios=in.nextInt();
		
		for (int i=0; i<numOfPhysios; i++) {
			Physiotherapist p1=new Physiotherapist (i, in.nextDouble());
			physiotherapistQueue.add(p1);
		}
		numOfCoaches=in.nextInt();
		numOfMasseurs=in.nextInt();
		
		
		while (!collectiveQueue.isEmpty()) {
			if (trainingQueue.isEmpty() && massageQueue.isEmpty() 
					&& physiotherapyQueue.isEmpty()) {
				if (!ongoingsQueue.isEmpty() && ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						if (physiotherapistQueue.isEmpty()) {
							physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
							if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
								maxPhysiotherapyLength=physiotherapyQueue.size();
							}
						}
						else {
							physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
							physioMap.get(ongoingsQueue.peek()).adjustTime();
							totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
							ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
							ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
						}
						numOfCoaches++;
					}
					else if (ongoingsQueue.peek().getType().equals("m")) {
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						numOfMasseurs++;
						totalMassageAmount++;
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else if (massageQueue.isEmpty() && physiotherapyQueue.isEmpty()) {
				
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
						trainingQueue.peek().adjustTime();
						ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
						totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
						if (physiotherapistQueue.isEmpty()) {
							physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
							if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
								maxPhysiotherapyLength=physiotherapyQueue.size();
							}
						}
						else {
							physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
							physioMap.get(ongoingsQueue.peek()).adjustTime();
							totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
							ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
							ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
							
						}
						ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
					}
					else if (ongoingsQueue.peek().getType().equals("m")) {
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						numOfMasseurs++;
						totalMassageAmount++;
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else if (trainingQueue.isEmpty() && physiotherapyQueue.isEmpty()) {
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						if (physiotherapistQueue.isEmpty()) {
							physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
							if (physiotherapyQueue.size()>maxTrainingLength) {
								maxPhysiotherapyLength=physiotherapyQueue.size();
							}
						}
						else {
							physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
							physioMap.get(ongoingsQueue.peek()).adjustTime();
							totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
							ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
							ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
						}
						numOfCoaches++;
					}
					
					
					else if (ongoingsQueue.peek().getType().equals("m")) {
						massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
						massageQueue.peek().adjustTime();
						ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
						totalWaitingMassage+=massageQueue.peek().getQueueDuration();
						massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						totalMassageAmount++;
						ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else if (trainingQueue.isEmpty() && massageQueue.isEmpty()) {
				
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
						numOfCoaches++;
					}
					else if (ongoingsQueue.peek().getType().equals("m")) {
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						numOfMasseurs++;
						totalMassageAmount++;
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
						physiotherapyQueue.peek().adjustTime();
						physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
						physiotherapyQueue.peek().adjustTime();
						ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
						totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
						totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
						physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else if (trainingQueue.isEmpty()) {
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
						numOfCoaches++;
					}
					
					else if (ongoingsQueue.peek().getType().equals("m")) {
						massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
						massageQueue.peek().adjustTime();
						ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
						totalWaitingMassage+=massageQueue.peek().getQueueDuration();
						massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						totalMassageAmount++;
						ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
						physiotherapyQueue.peek().adjustTime();
						physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
						physiotherapyQueue.peek().adjustTime();
						ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
						totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
						totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
						physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else if (massageQueue.isEmpty()) {
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
						trainingQueue.peek().adjustTime();
						ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
						totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
						ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
					}
				
					else if (ongoingsQueue.peek().getType().equals("m")) {
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						numOfMasseurs++;
						totalMassageAmount++;
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
						physiotherapyQueue.peek().adjustTime();
						physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
						physiotherapyQueue.peek().adjustTime();
						ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
						totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
						totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
						physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else if (physiotherapyQueue.isEmpty()) {
				
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
						trainingQueue.peek().adjustTime();
						ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
						totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
						if (physiotherapistQueue.isEmpty()) {
							physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
							if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
								maxPhysiotherapyLength=physiotherapyQueue.size();
							}
						}
						else {
							physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
							physioMap.get(ongoingsQueue.peek()).adjustTime(); 
							totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
							ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
							ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
							
						}
						ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
					}
					else if (ongoingsQueue.peek().getType().equals("m")) {
						massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
						massageQueue.peek().adjustTime();
						ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
						totalWaitingMassage+=massageQueue.peek().getQueueDuration();
						massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						totalMassageAmount++;
						ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
			else {
				if (ongoingsQueue.peek().getLeavingTime()<=collectiveQueue.peek().getTime()) {
					finishTime=ongoingsQueue.peek().getLeavingTime();
					if (ongoingsQueue.peek().getType().equals("t")) {
						trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
						trainingQueue.peek().adjustTime();
						ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
						totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
						ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
					}
				
					else if (ongoingsQueue.peek().getType().equals("m")) {
						massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
						massageQueue.peek().adjustTime();
						ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
						totalWaitingMassage+=massageQueue.peek().getQueueDuration();
						massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						totalMassageAmount++;
						ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
					}
					else {
						physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
						physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
						physiotherapyQueue.peek().adjustTime();
						physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
						physiotherapyQueue.peek().adjustTime();
						ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
						totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
						totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
						physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
						ongoingsQueue.poll().getPlayer().setInEvent(false);
						ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
					}
				}
				else {
					finishTime=collectiveQueue.peek().getTime();
					if (collectiveQueue.peek().getType().equals("t")) {
						if (!collectiveQueue.peek().getPlayer().getInEvent()) {
							collectiveQueue.peek().getPlayer().setInEvent(true);
							totalTrainingDuration+=collectiveQueue.peek().getDuration();
							if (numOfCoaches>0) {
								ongoingsQueue.add(ongoingTrainingMap.get(trainingMap.get(collectiveQueue.poll())));
								numOfCoaches--;
							}
							else {
								trainingQueue.add(trainingMap.get(collectiveQueue.poll()));
								if (trainingQueue.size()>maxTrainingLength) {
									maxTrainingLength=trainingQueue.size();
								}
							}
							totalTrainingAmount++;
						}
						else {
							cancelledAttempts++;
							collectiveQueue.poll();
						}
					}
					else {
						if ((collectiveQueue.peek().getPlayer().getMassageCount())<3) {	
							if (!collectiveQueue.peek().getPlayer().getInEvent()) {
								collectiveQueue.peek().getPlayer().setInEvent(true);
								collectiveQueue.peek().getPlayer().setMassageCount
								(collectiveQueue.peek().getPlayer().getMassageCount()+1);
								totalMassageDuration+=collectiveQueue.peek().getDuration();
								if (numOfMasseurs>0) {
									ongoingsQueue.add(ongoingMassageMap.get(massageMap.get(collectiveQueue.poll())));
									numOfMasseurs--;
								}
								else {
									massageQueue.add(massageMap.get(collectiveQueue.poll()));
									if (massageQueue.size()>maxMassageLength) {
										maxMassageLength=massageQueue.size();
									}
								}
							}
							else {
								cancelledAttempts++;
								collectiveQueue.poll();
							}
						}
						else {
							invalidAttempts++;
							collectiveQueue.poll();
						}
					}
				}
			}
		}
		while (!trainingQueue.isEmpty()) {
			if (massageQueue.isEmpty() && physiotherapyQueue.isEmpty()) {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
					trainingQueue.peek().adjustTime();
					ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
					totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
					if (physiotherapistQueue.isEmpty()) {
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
					}
					else {
						physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
						physioMap.get(ongoingsQueue.peek()).adjustTime();
						totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
						ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
						ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
						
					}
					ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
				}
				else if (ongoingsQueue.peek().getType().equals("m")) {
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					numOfMasseurs++;
					totalMassageAmount++;
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
				}
			}
			else if (massageQueue.isEmpty()) {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
					trainingQueue.peek().adjustTime();
					ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
					totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
					physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
					if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
						maxPhysiotherapyLength=physiotherapyQueue.size();
					}
					ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
				}
				else if (ongoingsQueue.peek().getType().equals("m")) {
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					numOfMasseurs++;
					totalMassageAmount++;
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
					physiotherapyQueue.peek().adjustTime();
					physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
					physiotherapyQueue.peek().adjustTime();
					ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
					totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
					totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
					physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
				}
			}
			else if (physiotherapyQueue.isEmpty()) {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
					trainingQueue.peek().adjustTime();
					ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
					totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
					if (physiotherapistQueue.isEmpty()) {
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
					}
					else {
						physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
						physioMap.get(ongoingsQueue.peek()).adjustTime();
						totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
						ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
						ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
						
					}
					ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
				}
				else if (ongoingsQueue.peek().getType().equals("m")) {
					massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
					massageQueue.peek().adjustTime();
					ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
					totalWaitingMassage+=massageQueue.peek().getQueueDuration();
					massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					totalMassageAmount++;
					ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
				}
			}
			else {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					trainingQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-trainingQueue.peek().getEntranceTime());
					trainingQueue.peek().adjustTime();
					ongoingTrainingMap.get(trainingQueue.peek()).setLeavingTime(trainingQueue.peek().getLeavingTime());
					totalWaitingTraining=totalWaitingTraining+trainingQueue.peek().getQueueDuration();
					physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
					if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
						maxPhysiotherapyLength=physiotherapyQueue.size();
					}
					ongoingsQueue.add(ongoingTrainingMap.get(trainingQueue.poll()));
				}
				else if (ongoingsQueue.peek().getType().equals("m")) {
					massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
					massageQueue.peek().adjustTime();
					ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
					totalWaitingMassage+=massageQueue.peek().getQueueDuration();
					massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					totalMassageAmount++;
					ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
					physiotherapyQueue.peek().adjustTime();
					physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
					physiotherapyQueue.peek().adjustTime();
					ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
					totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
					totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
					physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
				}	
			}
		}
		while (!massageQueue.isEmpty() || !physiotherapyQueue.isEmpty() || !ongoingsQueue.isEmpty()) {
			if (massageQueue.isEmpty() && physiotherapyQueue.isEmpty()) {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					if (physiotherapistQueue.isEmpty()) {
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
					}
					else {
						physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
						physioMap.get(ongoingsQueue.peek()).adjustTime();
						totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
						ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
						ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
					}
					numOfCoaches++;
				}
				else if (ongoingsQueue.peek().getType().equals("m")) {
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					numOfMasseurs++;
					totalMassageAmount++;
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
				}
			}
			else if (massageQueue.isEmpty()) {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
					if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
						maxPhysiotherapyLength=physiotherapyQueue.size();
					}
					numOfCoaches++;
				}
			
				else if (ongoingsQueue.peek().getType().equals("m")) {
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					numOfMasseurs++;
					totalMassageAmount++;
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
					physiotherapyQueue.peek().adjustTime();
					physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
					physiotherapyQueue.peek().adjustTime();
					ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
					totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
					totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
					physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
				}
			}
			else if (physiotherapyQueue.isEmpty()) {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					if (physiotherapistQueue.isEmpty()) {
						physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
						if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
							maxPhysiotherapyLength=physiotherapyQueue.size();
						}
					}
					else {
						physioMap.get(ongoingsQueue.peek()).setPhysiotherapist(physiotherapistQueue.poll());
						physioMap.get(ongoingsQueue.peek()).adjustTime();
						totalPhysiotherapyDuration+=physioMap.get(ongoingsQueue.peek()).getDuration();
						ongoingPhysioMap.get(physioMap.get(ongoingsQueue.peek())).setLeavingTime(physioMap.get(ongoingsQueue.peek()).getLeavingTime());
						ongoingsQueue.add(ongoingPhysioMap.get(physioMap.get(ongoingsQueue.poll())));
					}
					numOfCoaches++;
				}
				else if (ongoingsQueue.peek().getType().equals("m")) {
					massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
					massageQueue.peek().adjustTime();
					ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
					totalWaitingMassage+=massageQueue.peek().getQueueDuration();
					massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					totalMassageAmount++;
					ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
				}
			}
			else {
				finishTime=ongoingsQueue.peek().getLeavingTime();
				if (ongoingsQueue.peek().getType().equals("t")) {
					physiotherapyQueue.add(physioMap.get(ongoingsQueue.poll()));
					if (physiotherapyQueue.size()>maxPhysiotherapyLength) {
						maxPhysiotherapyLength=physiotherapyQueue.size();
					}
					numOfCoaches++;
				}
				
				else if (ongoingsQueue.peek().getType().equals("m")) {
					massageQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-massageQueue.peek().getEntranceTime());
					massageQueue.peek().adjustTime();
					ongoingMassageMap.get(massageQueue.peek()).setLeavingTime(massageQueue.peek().getLeavingTime());
					totalWaitingMassage+=massageQueue.peek().getQueueDuration();
					massageQueue.peek().getPlayer().setTotalQueueDuration1(massageQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					totalMassageAmount++;
					ongoingsQueue.add(ongoingMassageMap.get(massageQueue.poll()));
				}
				else {
					physiotherapistQueue.add(ingoingPhysioMap.get(ongoingsQueue.peek()).getPhysiotherapist());
					physiotherapyQueue.peek().setPhysiotherapist(physiotherapistQueue.poll());
					physiotherapyQueue.peek().adjustTime();
					physiotherapyQueue.peek().setQueueDuration(ongoingsQueue.peek().getLeavingTime()-physiotherapyQueue.peek().getEntranceTime());
					physiotherapyQueue.peek().adjustTime();
					ongoingPhysioMap.get(physiotherapyQueue.peek()).setLeavingTime(physiotherapyQueue.peek().getLeavingTime());
					totalWaitingPhysiotherapy+=physiotherapyQueue.peek().getQueueDuration();
					totalPhysiotherapyDuration+=physiotherapyQueue.peek().getDuration();
					physiotherapyQueue.peek().getTraining().getPlayer().setTotalQueueDuration2(physiotherapyQueue.peek().getQueueDuration());
					ongoingsQueue.poll().getPlayer().setInEvent(false);
					ongoingsQueue.add(ongoingPhysioMap.get(physiotherapyQueue.poll()));
				}	
			}
		
			
			
		
			
			
		
			
			
		}
		
		out.println(maxTrainingLength);
		out.println(maxPhysiotherapyLength);
		out.println(maxMassageLength);
		out.printf("%.3f", totalWaitingTraining/totalTrainingAmount);
		out.println();
		out.printf("%.3f", totalWaitingPhysiotherapy/totalTrainingAmount);
		out.println();
		out.printf("%.3f", totalWaitingMassage/totalMassageAmount);
		out.println();
		out.printf("%.3f", totalTrainingDuration/totalTrainingAmount);
		out.println();
		out.printf("%.3f", totalPhysiotherapyDuration/totalTrainingAmount);
		out.println();
		out.printf("%.3f", totalMassageDuration/totalMassageAmount);
		out.println();
		out.printf("%.3f", (totalWaitingTraining+totalWaitingPhysiotherapy+totalPhysiotherapyDuration+totalTrainingDuration)/totalTrainingAmount);
		out.println();
		
		
		for (Player s:players.values()) {
			if (s.getMassageCount()==3) {
				if (s.getTotalQueueDuration1()<leastMassageDuration || leastMassageDuration==-1) {
					leastMassageDuration=s.getTotalQueueDuration1();
					leastMassagePerson=s.getId();
				}
				else if (s.getTotalQueueDuration1()==leastMassageDuration ) {
					if(s.getId()<leastMassagePerson) {
						leastMassagePerson=s.getId();
					}
				}
			}
		}
		for (Player s:players.values()) {
			if (s.getTotalQueueDuration2()>maxPhysiotherapyDuration) {
				maxPhysiotherapyDuration=s.getTotalQueueDuration2();
				maxPhysiotherapyPerson=s.getId();
			}
			else if (s.getTotalQueueDuration1()==maxPhysiotherapyDuration ) {
				if(s.getId()<maxPhysiotherapyPerson) {
					maxPhysiotherapyPerson=s.getId();
				}
			}
		}
		out.print(maxPhysiotherapyPerson);
		out.print(" ");
		out.printf("%.3f", maxPhysiotherapyDuration);
		out.println();
		out.print(leastMassagePerson);
		out.print(" ");
		out.printf("%.3f", leastMassageDuration);
		out.println();
		out.println(invalidAttempts);
		out.println(cancelledAttempts);
		out.printf("%.3f", finishTime);
	}

}
