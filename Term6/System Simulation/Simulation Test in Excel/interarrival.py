import random
import math
# import xlwt
# from xlwt import Workbook

# This program creates 10 days of interarrival times using the NSPP 
# acceptance-rejection algorithm.
# In order to write the results to an excel file uncomment the commented code.

# wb = Workbook()
# sheet1 = wb.add_sheet("Sheet 1")
# sheet1.write(0, 0, "interarrival")

interarrival_times = []
# The arrival rate of different intervals
arrival_rates = [1/176.132, 1/635.844] 
# The time an arrival has last occured
last_arrival = 0
# Current time 
t = 0
# lambda star from the NSPP algorithm
lambda_star = max(arrival_rates)
 

while t <= 10 * 24 * 60 * 60: # stops when the time exceeds 10 days

    # Creates a interarrival time and adds it to current time
    random_number = random.uniform(0, 1) # R is generated using uniform dist.
    big_e = -1 * (1/lambda_star) * math.log(random_number)
    t += big_e

    random_number = random.uniform(0, 1) # R is generated using uniform dist.
    interval = int(t // 43200) % 2
    # lambda is the arrival rate of the current interval
    lambda_ = arrival_rates[interval] 
    # If the R is smaller then lambda/lambda_star creates an arrival
    if random_number < (lambda_ / lambda_star):
        # Since when there is an rejected arrival, big_e (created interarrival)
        # accumulates, instead of keeping track of rejected big_e, we calculate 
        # the interarrival by keeping track of the last arrival and subtracting it
        # from the current time when an arrival occurs
        interarrival = t - last_arrival

        # sheet1.write(i+1, 0, interarrival)

        # keeps the interarrival time in a list
        interarrival_times.append(interarrival) 

        last_arrival = t # updates the last arrival to current time
            
# wb.save("intertimes.xls")
