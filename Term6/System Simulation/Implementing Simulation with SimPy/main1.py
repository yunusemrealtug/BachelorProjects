import simpy
import random
import matplotlib.pyplot as plt

RANDOM_SEED = 1000 
NUMBER_OF_CUSTOMERS = 500 
LOW_INTARR=7.96 
UPP_INTARR=9.01
INTERARRIVAL_RATE = 2/(7.96+9.01)
SERVICE_RATE = 0.15 #IT WILL CHANGE FOR EACH UTILIZATION VALUE
NUM_OF_SERVERS=3

random.seed(RANDOM_SEED)

customers = []#List of customers
utilizations=[0.6,0.7,0.8,0.9]
total_customer_list=[]
average_sojourns=[]
average_cumulatives=[]
upp_confidence_sojourn=[]
low_confidence_sojourn=[]
low_confidence_cumulative=[]
upp_confidence_cumulative=[]
customerNum=0 #represents number of customers at the moment
cumCustomerTime=0 #sum of all customers' sojourn times which have been at the system until the determined time
last_event_time=0 #the time of last departure or arrival whatever is last
"""
#FOR PHASE ONE
class Customer(object): 
    def __init__(self, name, env, operator):
        self.env = env
        self.name = name
        self.operator = operator
        self.arrival_t = self.env.now
        self.waiting_t=None
        self.service_t=None
        self.sojourn_t=None #this is total time of each user on the system
        self.cumulative=None #this is the average number of customers in the system until this customer arrives 
        self.action = env.process(self.call())
    
    #customers enters the system, cumulative variable assigned as cumCustomerTime variable and last event is adjusted as arrival of these customer
    #Then customer enters the server, cumCustomerTime calculated for time after last event time as in arrival, 
    #system has one customer less now.
    def call(self):
        global cumCustomerTime
        global last_event_time
        global customerNum
        cumCustomerTime+=(self.env.now-last_event_time)*customerNum
        self.cumulative=cumCustomerTime/self.arrival_t
        customerNum+=1
        last_event_time=self.env.now
        with self.operator.request() as req:
            yield req
            self.waiting_t=self.env.now - self.arrival_t
            yield self.env.process(self.ask_question())
            cumCustomerTime+=(self.env.now-last_event_time)*customerNum
            customerNum-=1
            last_event_time=self.env.now

    #new service time is produced here, and used for the customers' statistics
    def ask_question(self):
        duration = random.expovariate(SERVICE_RATE)
        self.service_t=duration
        yield self.env.timeout(duration)
        self.sojourn_t=self.service_t+self.waiting_t
        
#interarrival time is generated for the simulation here
def customer_generator(env, operator):
    for i in range(NUMBER_OF_CUSTOMERS):
        yield env.timeout(random.uniform(7.96,9.01))
        customer = Customer('Cust %s' %(i+1), env, operator)
        customers.append(customer)  

#firstly we put each utilization value in a list and pick one respectively
for util_val in utilizations:
    #Clearing all lists that we use and calculating service rate for each utilization value 
    SERVICE_RATE=INTERARRIVAL_RATE/(NUM_OF_SERVERS*util_val)
    total_customer_list.clear()
    average_sojourns.clear()
    average_cumulatives.clear()
    upp_confidence_sojourn.clear()
    low_confidence_sojourn.clear()
    upp_confidence_cumulative.clear()
    low_confidence_cumulative.clear()
    #10 runs
    for run in range(10):
        #reseting 3 global variables and clearing customers array for each run
        customerNum=0
        cumCustomerTime=0
        last_event_time=0
        customers.clear()
        
        #get different random seeds

        RANDOM_SEED+=10
        random.seed(RANDOM_SEED)

        #simulating
        env = simpy.Environment()
        operator = simpy.Resource(env, capacity = NUM_OF_SERVERS)
        env.process(customer_generator(env, operator))
        env.run()

        #the customers of each run gathered in one list
        
        total_customer_list.append(customers.copy())
    
    
    #calculating and measuring the necessary data and its upper and lower bounds   
    for customer in range (NUMBER_OF_CUSTOMERS):
        val=0 #ensemble average of sojourn times
        val2=0 #ensemble average of L(t) values which t is arrival time of each customer
        deviationval=0 #standard deviation of sojourn times 
        deviationval2=0 #standard deviation of L(t) values
        for elem in range(10):
            val+=total_customer_list[elem][customer].sojourn_t/10
            val2+=total_customer_list[elem][customer].cumulative/10
        for elem in range(10):
            deviationval+=((total_customer_list[elem][customer].sojourn_t-val)**2)/9
            deviationval2+=((total_customer_list[elem][customer].cumulative-val2)**2)/9
        deviationval=deviationval**0.5
        deviationval2=deviationval2**0.5
        average_sojourns.append(val)
        average_cumulatives.append(val2)
        upp_confidence_sojourn.append(val+2.262*deviationval/10**0.5) 
        low_confidence_sojourn.append(val-2.262*deviationval/10**0.5)
        upp_confidence_cumulative.append(val2+2.262*deviationval2/10**0.5)
        low_confidence_cumulative.append(val2-2.262*deviationval2/10**0.5)
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), average_sojourns, label="mean")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), upp_confidence_sojourn, label="ub")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), low_confidence_sojourn, label="lb")
    plt.legend()
    plt.show()
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), average_cumulatives, label="mean")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), upp_confidence_cumulative, label="ub")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), low_confidence_cumulative, label="lb")
    plt.show()
    #clearing the lists for other , we are doing the same things for also 30 run.
    average_sojourns.clear()
    average_cumulatives.clear()
    total_customer_list.clear()
    upp_confidence_sojourn.clear()
    low_confidence_sojourn.clear()
    upp_confidence_cumulative.clear()
    low_confidence_cumulative.clear()
    for run in range(30):
        customerNum=0
        cumCustomerTime=0
        last_event_time=0
        customers.clear()
        RANDOM_SEED+=10
        random.seed(RANDOM_SEED)
        env = simpy.Environment()
        operator = simpy.Resource(env, capacity = NUM_OF_SERVERS)
        env.process(customer_generator(env, operator))
        env.run() 
        total_customer_list.append(customers.copy())
    for customer in range (NUMBER_OF_CUSTOMERS):
        val=0
        val2=0
        deviationval=0
        deviationval2=0
        for elem in range(30):
            val+=total_customer_list[elem][customer].sojourn_t/30
            val2+=total_customer_list[elem][customer].cumulative/30
        for elem in range(30):
            deviationval+=((total_customer_list[elem][customer].sojourn_t-val)**2)/29
            deviationval2+=((total_customer_list[elem][customer].cumulative-val2)**2)/29
        deviationval=deviationval**0.5
        deviationval2=deviationval2**0.5
        average_sojourns.append(val)
        average_cumulatives.append(val2)
        upp_confidence_sojourn.append(val+2.045*deviationval/30**0.5)
        low_confidence_sojourn.append(val-2.045*deviationval/30**0.5)
        upp_confidence_cumulative.append(val2+2.045*deviationval2/30**0.5)
        low_confidence_cumulative.append(val2-2.045*deviationval2/30**0.5)
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), average_sojourns, label="mean")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), upp_confidence_sojourn, label="ub")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), low_confidence_sojourn, label="lb")
    plt.legend()
    plt.show()
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), average_cumulatives, label="mean")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), upp_confidence_cumulative, label="ub")
    plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), low_confidence_cumulative, label="lb")
    plt.show()
    print("simulation "+str(util_val)+" ended.")"""

#FOR PHASE TWO PART 1
#There is no change in this class but only removing the quotes you can run the spesific part
class Customer(object):
    def __init__(self, name, env, operator):
        self.env = env
        self.name = name
        self.operator = operator
        self.arrival_t = self.env.now
        self.waiting_t=None
        self.service_t=None
        self.sojourn_t=None
        self.cumulative=None
        self.action = env.process(self.call())
    
    
    def call(self):
        global cumCustomerTime
        global last_event_time
        global customerNum
        cumCustomerTime+=(self.env.now-last_event_time)*customerNum
        self.cumulative=cumCustomerTime/self.arrival_t
        customerNum+=1
        last_event_time=self.env.now
        with self.operator.request() as req:
            yield req
            self.waiting_t=self.env.now - self.arrival_t
            yield self.env.process(self.ask_question())
            cumCustomerTime+=(self.env.now-last_event_time)*customerNum
            customerNum-=1
            last_event_time=self.env.now
    def ask_question(self):
        duration = random.expovariate(SERVICE_RATE)
        self.service_t=duration
        yield self.env.timeout(duration)
        self.sojourn_t=self.service_t+self.waiting_t
        

def customer_generator(env, operator):
    for i in range(NUMBER_OF_CUSTOMERS):
        yield env.timeout(random.uniform(7.96,9.01))
        customer = Customer('Cust %s' %(i+1), env, operator)
        customers.append(customer)  

#The algorithm works exactly
NUMBER_OF_CUSTOMERS=20
SERVICE_RATE=INTERARRIVAL_RATE/(NUM_OF_SERVERS*0.8)
total_customer_list.clear()
average_sojourns.clear()
average_cumulatives.clear()
upp_confidence_sojourn.clear()
low_confidence_sojourn.clear()
upp_confidence_cumulative.clear()
low_confidence_cumulative.clear()
for run in range(10):
    customerNum=0
    cumCustomerTime=0
    last_event_time=0
    customers.clear()
    RANDOM_SEED+=10
    random.seed(RANDOM_SEED)
    env = simpy.Environment()
    operator = simpy.Resource(env, capacity = NUM_OF_SERVERS)
    env.process(customer_generator(env, operator))
    env.run() 
    total_customer_list.append(customers.copy())
for customer in range (NUMBER_OF_CUSTOMERS):
    val=0
    val2=0
    deviationval=0
    deviationval2=0
    for elem in range(10):
        val+=total_customer_list[elem][customer].sojourn_t/10
        val2+=total_customer_list[elem][customer].cumulative/10
    for elem in range(10):
        deviationval+=((total_customer_list[elem][customer].sojourn_t-val)**2)/9
        deviationval2+=((total_customer_list[elem][customer].cumulative-val2)**2)/9
    deviationval=deviationval**0.5
    deviationval2=deviationval2**0.5
    average_sojourns.append(val)
    average_cumulatives.append(val2)
    upp_confidence_sojourn.append(val+2.262*deviationval/10**0.5)
    low_confidence_sojourn.append(val-2.262*deviationval/10**0.5)
    upp_confidence_cumulative.append(val2+2.262*deviationval2/10**0.5)
    low_confidence_cumulative.append(val2-2.262*deviationval2/10**0.5)
plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), average_sojourns, label="mean")
plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), upp_confidence_sojourn, label="ub")
plt.plot(range(1,NUMBER_OF_CUSTOMERS+1), low_confidence_sojourn, label="lb")
plt.legend()
plt.show()

#FOR PHASE TWO PART 2, there is a significant difference at customer generator

class Customer(object):
    def __init__(self, name, env, operator):
        self.env = env
        self.name = name
        self.operator = operator
        self.arrival_t = self.env.now
        self.waiting_t=None
        self.service_t=None
        self.sojourn_t=None
        self.cumulative=None
        self.action = env.process(self.call())
    
    
    def call(self):
        global cumCustomerTime
        global last_event_time
        global customerNum
        cumCustomerTime+=(self.env.now-last_event_time)*customerNum
        if cumCustomerTime!=0:
            self.cumulative=cumCustomerTime/self.arrival_t
        else:
            self.cumulative=0
        customerNum+=1
        last_event_time=self.env.now
        with self.operator.request() as req:
            yield req
            self.waiting_t=self.env.now - self.arrival_t
            yield self.env.process(self.ask_question())
            cumCustomerTime+=(self.env.now-last_event_time)*customerNum
            customerNum-=1
            last_event_time=self.env.now
    def ask_question(self):
        duration = random.expovariate(SERVICE_RATE)
        self.service_t=duration
        yield self.env.timeout(duration)
        self.sojourn_t=self.service_t+self.waiting_t
        

def customer_generator(env, operator):
    #four customers are at the system.
    for i in range(4):  # Generate the first four customers immediately
        customer = Customer('Cust %s' % (i+1), env, operator)
        customers.append(customer)
        
    for i in range(4, NUMBER_OF_CUSTOMERS):  # Generate the remaining customers with timeouts
        yield env.timeout(random.uniform(7.96, 9.01))
        customer = Customer('Cust %s' % (i+1), env, operator)
        customers.append(customer)
#we have 24 customers and do not take stats of first four not to see advantages of empty queue for a customer.
NUMBER_OF_CUSTOMERS=24
SERVICE_RATE=INTERARRIVAL_RATE/(NUM_OF_SERVERS*0.8)
total_customer_list.clear()
average_sojourns.clear()
average_cumulatives.clear()
upp_confidence_sojourn.clear()
low_confidence_sojourn.clear()
upp_confidence_cumulative.clear()
low_confidence_cumulative.clear()
for run in range(10):
    customerNum=0
    cumCustomerTime=0
    last_event_time=0
    customers.clear()
    RANDOM_SEED+=10
    random.seed(RANDOM_SEED)
    env = simpy.Environment()
    operator = simpy.Resource(env, capacity = NUM_OF_SERVERS)
    env.process(customer_generator(env, operator))
    env.run() 
    total_customer_list.append(customers.copy())
for customer in range (4, NUMBER_OF_CUSTOMERS):
    val=0
    val2=0
    deviationval=0
    deviationval2=0
    for elem in range(10):
        val+=total_customer_list[elem][customer].sojourn_t/10
        val2+=total_customer_list[elem][customer].cumulative/10
    for elem in range(10):
        deviationval+=((total_customer_list[elem][customer].sojourn_t-val)**2)/9
        deviationval2+=((total_customer_list[elem][customer].cumulative-val2)**2)/9
    deviationval=deviationval**0.5
    deviationval2=deviationval2**0.5
    average_sojourns.append(val)
    average_cumulatives.append(val2)
    upp_confidence_sojourn.append(val+2.262*deviationval/10**0.5)
    low_confidence_sojourn.append(val-2.262*deviationval/10**0.5)
    upp_confidence_cumulative.append(val2+2.262*deviationval2/10**0.5)
    low_confidence_cumulative.append(val2-2.262*deviationval2/10**0.5)
plt.plot(range(5,NUMBER_OF_CUSTOMERS+1), average_sojourns, label="mean")
plt.plot(range(5,NUMBER_OF_CUSTOMERS+1), upp_confidence_sojourn, label="ub")
plt.plot(range(5,NUMBER_OF_CUSTOMERS+1), low_confidence_sojourn, label="lb")
plt.legend()
plt.show()


#FOR PHASE TWO PART 3
class Customer(object):
    def __init__(self, name, env, operator):
        self.env = env
        self.name = name
        self.operator = operator
        self.arrival_t = self.env.now
        self.waiting_t=None
        self.service_t=None
        self.sojourn_t=None
        self.cumulative=None
        self.action = env.process(self.call())
    
    
    def call(self):
        global cumCustomerTime
        global last_event_time
        global customerNum
        cumCustomerTime+=(self.env.now-last_event_time)*customerNum
        self.cumulative=cumCustomerTime/self.arrival_t
        customerNum+=1
        last_event_time=self.env.now
        with self.operator.request() as req:
            yield req
            self.waiting_t=self.env.now - self.arrival_t
            yield self.env.process(self.ask_question())
            cumCustomerTime+=(self.env.now-last_event_time)*customerNum
            customerNum-=1
            last_event_time=self.env.now
    def ask_question(self):
        duration = random.expovariate(SERVICE_RATE)
        self.service_t=duration
        yield self.env.timeout(duration)
        self.sojourn_t=self.service_t+self.waiting_t
        

def customer_generator(env, operator):
    for i in range(NUMBER_OF_CUSTOMERS):
        yield env.timeout(random.uniform(7.96,9.01))
        customer = Customer('Cust %s' %(i+1), env, operator)
        customers.append(customer)  
#we determined number of customers as 100, so 120 customers will enter the system.
NUMBER_OF_CUSTOMERS=120
SERVICE_RATE=INTERARRIVAL_RATE/(NUM_OF_SERVERS*0.8)
total_customer_list.clear()
average_sojourns.clear()
average_cumulatives.clear()
upp_confidence_sojourn.clear()
low_confidence_sojourn.clear()
upp_confidence_cumulative.clear()
low_confidence_cumulative.clear()
for run in range(10):
    customerNum=0
    cumCustomerTime=0
    last_event_time=0
    customers.clear()
    RANDOM_SEED+=10
    random.seed(RANDOM_SEED)
    env = simpy.Environment()
    operator = simpy.Resource(env, capacity = NUM_OF_SERVERS)
    env.process(customer_generator(env, operator))
    env.run() 
    total_customer_list.append(customers.copy())
for customer in range (20):
    val=0
    val2=0
    deviationval=0
    deviationval2=0
    for elem in range(10):
        val+=total_customer_list[elem][NUMBER_OF_CUSTOMERS-customer-1].sojourn_t/10
        val2+=total_customer_list[elem][NUMBER_OF_CUSTOMERS-customer-1].cumulative/10
    for elem in range(10):
        deviationval+=((total_customer_list[elem][NUMBER_OF_CUSTOMERS-customer-1].sojourn_t-val)**2)/9
        deviationval2+=((total_customer_list[elem][NUMBER_OF_CUSTOMERS-customer-1].cumulative-val2)**2)/9
    deviationval=deviationval**0.5
    deviationval2=deviationval2**0.5
    average_sojourns.append(val)
    average_cumulatives.append(val2)
    upp_confidence_sojourn.append(val+2.262*deviationval/10**0.5)
    low_confidence_sojourn.append(val-2.262*deviationval/10**0.5)
    upp_confidence_cumulative.append(val2+2.262*deviationval2/10**0.5)
    low_confidence_cumulative.append(val2-2.262*deviationval2/10**0.5)
plt.plot(range(101,NUMBER_OF_CUSTOMERS+1), average_sojourns, label="mean")
plt.plot(range(101,NUMBER_OF_CUSTOMERS+1), upp_confidence_sojourn, label="ub")
plt.plot(range(101,NUMBER_OF_CUSTOMERS+1), low_confidence_sojourn, label="lb")
plt.legend()
plt.show()
