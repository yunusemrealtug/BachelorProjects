f = open("crime_scene.txt", "r")
g=f.read()
f_list=g.split()
evidence_ids=[]
weights=[]
times=[]
evidence_values=[]

for number in range(len(f_list)):
    if number>2:
        f_list[number] = int(f_list[number])
        if number%4==3:
            evidence_ids.append(f_list[number])
        elif number%4==0:
            weights.append(f_list[number])
        elif number%4==1:
            times.append(f_list[number])
        else:
            evidence_values.append(f_list[number])

def restriction(remaining_limit,i, list1, list2,list3):
    if i==len(list1):
        return 0, []
    if remaining_limit-list2[i]>=0:
        collected_value, collected_list=restriction(remaining_limit-list2[i],i+1, list1, list2, list3)
        collected_value=collected_value+list3[i]
        collected_list.append(list1[i])
    else:
        collected_value=0
        collected_list=0

    collected_value_dont_take, collected_list_dont_take=restriction(remaining_limit, i+1, list1, list2, list3)
    if collected_value>collected_value_dont_take:
        return collected_value, collected_list
    else:
        return collected_value_dont_take, collected_list_dont_take

def restriction2(remaining_limit1, remaining_limit2,i, list1, list2,list3,list4):
    if i==len(list1):
        return 0, []
    if remaining_limit1-list2[i]>=0 and remaining_limit2-list3[i]>=0:
        collected_value, collected_list=restriction2(remaining_limit1-list2[i], remaining_limit2-list3[i], i+1, list1, list2, list3, list4)
        collected_value=collected_value+list4[i]
        collected_list.append(list1[i])
    else:
        collected_value=0
        collected_list=0

    collected_value_dont_take, collected_list_dont_take=restriction2(remaining_limit1, remaining_limit2, i+1, list1, list2, list3, list4)
    if collected_value>collected_value_dont_take:
        return collected_value, collected_list
    else:
        return collected_value_dont_take, collected_list_dont_take

sort_count=0
def sort(lst):
    global sort_count
    for i in range(len(lst)):
        for j in range(len(lst)-1):
            if (lst[j]>lst[j+1]):
                lst[j],lst[j+1] = lst[j+1],lst[j]
                sort_count += 1
    return lst



total_value_1=(restriction(int(f_list[0]), 0, evidence_ids, weights, evidence_values)[0])
total_list_1=sort(restriction(int(f_list[0]), 0, evidence_ids, weights, evidence_values)[1])
total_value_2=(restriction(int(f_list[1]), 0, evidence_ids, times, evidence_values)[0])
total_list_2=sort(restriction(int(f_list[1]), 0, evidence_ids, times, evidence_values)[1])
total_value_3=(restriction2(int(f_list[0]), int(f_list[1]), 0, evidence_ids, weights, times, evidence_values)[0])
total_list_3=sort(restriction2(int(f_list[0]), int(f_list[1]), 0, evidence_ids, weights, times, evidence_values)[1])

solution_part1=open("solution_part1.txt", "w")
solution_part1.write(str(total_value_1))
solution_part1.write('\n')
for niko_bellic in total_list_1:
    solution_part1.write(str(niko_bellic))
    solution_part1.write(' ')
solution_part1.close()

solution_part2=open("solution_part2.txt", "w")
solution_part2.write(str(total_value_2))
solution_part2.write('\n')
for niko_bellic in total_list_2:
    solution_part2.write(str(niko_bellic))
    solution_part2.write(' ')
solution_part2.close()

solution_part3=open("solution_part3.txt", "w")
solution_part3.write(str(total_value_3))
solution_part3.write('\n')
for niko_bellic in total_list_3:
    solution_part3.write(str(niko_bellic))
    solution_part3.write(' ')
solution_part3.close()

f.close()
