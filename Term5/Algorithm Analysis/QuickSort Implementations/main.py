import random
import sys
import time
import copy


def quickSort (myList, low, high): # this is first basic quick sort we covered
    if (high>low):
        base=myList[low]
        right=high+1
        left=low
        while (left<right):
            left+=1
            right-=1
            while (myList[left]<base and left!=right):
                left+=1
            while (myList[right]>base):
                right=right-1
            if (left<right):
                temp=myList[left]
                myList[left]=myList[right]
                myList[right]=temp
        position=right
        myList[low]=myList[position]
        myList[position]=base
        quickSort (myList, low, position-1)
        quickSort (myList, position+1, high)
    

def quickSort2 (myList, low, high): #in that we select base randomly
    if (high>low):
        randomInt=random.randint(low, high) #random is select from the indexes
        temp=myList[low]
        myList[low]=myList[randomInt]
        myList[randomInt]=temp #randomth element and 0th element and interchanged
        right=high+1
        left=low
        base=myList[low]
        while (left<right):
            left+=1
            right-=1
            while (myList[left]<base and left!=right):
                left+=1
            while (myList[right]>base):
                right=right-1
            if (left<right):
                temp=myList[left]
                myList[left]=myList[right]
                myList[right]=temp
        position=right
        myList[low]=myList[position]
        myList[position]=base
        quickSort2 (myList, low, position-1)
        quickSort2 (myList, position+1, high)

def quickSort4 (myList, low, high): # for quick sort 4 we used median of three method
    if (high>low):
        middle=(low+high)//2
        if (myList[low]<myList[middle]): #median is chosen with nested if clauses
            if (myList[low]<myList[high]):
                if (myList[middle]<myList[high]):
                    median=middle
                else:
                    median=high
            else:
                median=low
        else:
            if (myList[low]>myList[high]):
                if (myList[middle]>myList[high]):
                    median=middle
                else:
                    median=high
            else:
                median=low

        temp=myList[low] #median is interchanged with 0th element
        myList[low]=myList[median]
        myList[median]=temp
        right=high+1
        left=low
        base=myList[low]
        while (left<right):
            left+=1
            right-=1
            while (myList[left]<base and left!=right):
                left+=1
            while (myList[right]>base):
                right=right-1
            if (left<right):
                temp=myList[left]
                myList[left]=myList[right]
                myList[right]=temp
        position=right
        myList[low]=myList[position]
        myList[position]=base
        quickSort4 (myList, low, position-1)
        quickSort4 (myList, position+1, high)
sys.setrecursionlimit(10000) 

#sample = open('samplefile.txt', 'w')
#sample1 = open('samplefilesorted.txt', 'w')
baseArray=[[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],]
#this list basically contains all data lists

for i in range(100):
    for j in range(6):
        baseArray[j].append(random.randint(1, 1000))
    for j in range(6):
        baseArray[j+6].append(random.randint(1, 75))
    for j in range(6):
        baseArray[j+12].append(random.randint(1, 25))
    for j in range(6):
        baseArray[j+18].append(1)
    


for i in range(1000):
    for j in range(6):
        baseArray[j+24].append(random.randint(1, 10000))
    for j in range(6):
        baseArray[j+30].append(random.randint(1, 750))
    for j in range(6):
        baseArray[j+36].append(random.randint(1, 250))
    for j in range(6):
        baseArray[j+42].append(1)

for i in range(10000):
    for j in range(6):
        baseArray[j+48].append(random.randint(1, 100000))
    for j in range(6):
        baseArray[j+54].append(random.randint(1, 7500))
    for j in range(6):
        baseArray[j+60].append(random.randint(1, 2500))
    for j in range(6):
        baseArray[j+66].append(1)

for i in range (12):
    baseArray[6*i+5].sort()
#for i in range (72):
#    print(baseArray[i], file=sample) 

def sorting1(): #to sort all lists with first type quick sort we used this method
    newArray=copy.deepcopy(baseArray) # deep copy is required for keeping base list unsorted

    for i in range(3):
        for j in range (4):
            averageDiff=0
            worstDiff=0
            for k in range (5):
                st = time.time_ns() #it sorts all lists
                quickSort(newArray[24*i+6*j+k], 0, (100*10**i)-1)
                et = time.time_ns()
                diff=(et-st)/1000000
                averageDiff+=diff
            averageDiff/=5
            #print(averageDiff, 8*i+2*j, file=sample1)

            st = time.time_ns() #the sorted list is sorted
            quickSort(newArray[24*i+6*j+5], 0, (100*10**i)-1)
            et = time.time_ns()
            worstDiff=(et-st)/1000000
            #print(worstDiff, 8*i+2*j+1, file=sample1)
               

def sorting2(): #this is for using second quicksort method
    newArray=copy.deepcopy(baseArray)


    for i in range(3):
        for j in range (4):
            averageDiff=0
            worstDiff=0
            for k in range (5):
                st = time.time_ns()
                quickSort2(newArray[24*i+6*j+k], 0, (100*10**i)-1)
                et = time.time_ns()
                diff=(et-st)/1000000
                averageDiff+=diff
            averageDiff/=5
            #print(averageDiff, 8*i+2*j, file=sample1)

            st = time.time_ns()
            quickSort2(newArray[24*i+6*j+5], 0, (100*10**i)-1)
            et = time.time_ns()
            worstDiff=(et-st)/1000000
            #print(worstDiff, 8*i+2*j+1, file=sample1)

    


def sorting3(): #this is for using third quicksort method
    newArray=copy.deepcopy(baseArray)        

    for i in range(3):
        for j in range (4):
            averageDiff=0
            worstDiff=0
            for k in range (5):
                st = time.time_ns()
                random.shuffle(newArray[24*i+6*j+k]) #we use basic quicksort, but before that shuffling is difference here 
                quickSort(newArray[24*i+6*j+k], 0, (100*10**i)-1)
                et = time.time_ns()
                diff=(et-st)/1000000
                averageDiff+=diff
            averageDiff/=5
            #print(averageDiff, 8*i+2*j, file=sample1)

            st = time.time_ns()
            random.shuffle(newArray[24*i+6*j+5])
            quickSort(newArray[24*i+6*j+5], 0, (100*10**i)-1)
            et = time.time_ns()
            worstDiff=(et-st)/1000000
            #print(worstDiff, 8*i+2*j+1, file=sample1)


    


def sorting4(): #it is for using quicksort4
    newArray=copy.deepcopy(baseArray)

    for i in range(3):
        for j in range (4):
            averageDiff=0
            worstDiff=0
            for k in range (5):
                st = time.time_ns()
                quickSort4(newArray[24*i+6*j+k], 0, (100*10**i)-1)
                et = time.time_ns()
                diff=(et-st)/1000000
                averageDiff+=diff
            averageDiff/=5
            #print(averageDiff, 8*i+2*j, file=sample1)

            st = time.time_ns()
            quickSort4(newArray[24*i+6*j+5], 0, (100*10**i)-1)
            et = time.time_ns()
            worstDiff=(et-st)/1000000
            #print(worstDiff, 8*i+2*j+1, file=sample1)




sorting1()
sorting2()
sorting3()
sorting4()







#sample.close()
#sample1.close()




