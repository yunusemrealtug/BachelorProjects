import sys

hiddenStates=[]
probabilities=[]


def forward (evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, t):
    if t==0:
        return [prior_prob,1-prior_prob]
    else:
        if evidence_list[t-1]=='T':
            res1, res2=forward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)[0],forward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)[1]
            possibility1=res1*transition_rain_rain
            possibility2=res2*transition_no_rain_rain
            value1=(possibility1+possibility2)*emission_rain_umbrella
            value2=(1-possibility1-possibility2)*transition_no_rain_umbrella
            
            norVal1=value1/(value1+value2)
            norVal2=value2/(value1+value2)
            return [norVal1,norVal2]
        else:
            res1, res2=forward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)[0],forward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)[1]
            possibility1=res1*transition_rain_rain
            possibility2=res2*transition_no_rain_rain
            value1=(possibility1+possibility2)*(1-emission_rain_umbrella)
            value2=(1-possibility1-possibility2)*(1-transition_no_rain_umbrella)
            norVal1=value1/(value1+value2)
            norVal2=value2/(value1+value2)
            return [norVal1,norVal2]
        
def likelihood_of_evidence(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, t):
    if t==1:
        if evidence_list[t-1]=='T':
           
            
            value1=prior_prob*emission_rain_umbrella
            value2=(1-prior_prob)*transition_no_rain_umbrella
            return [value1, value2]
        else:
            value1=prior_prob*(1-emission_rain_umbrella)
            value2=(1-prior_prob)*(1-transition_no_rain_umbrella)
            return [value1, value2]
        
    else:
        if evidence_list[t-1]=='T':
            [res1, res2]=likelihood_of_evidence(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)
            value1=res1*transition_rain_rain*emission_rain_umbrella+res2*transition_no_rain_rain*emission_rain_umbrella
            value2=res1*(1-transition_rain_rain)*transition_no_rain_umbrella+res2*(1-transition_no_rain_rain)*transition_no_rain_umbrella

            return [value1,value2]
            
        else:
            [res1, res2]=likelihood_of_evidence(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)
            
            value1=res1*transition_rain_rain*(1-emission_rain_umbrella)+res2*transition_no_rain_rain*(1-emission_rain_umbrella)
            value2=res1*(1-transition_rain_rain)*(1-transition_no_rain_umbrella)+res2*(1-transition_no_rain_rain)*(1-transition_no_rain_umbrella)
            return [value1,value2]
    
        
def backward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, t, k):
    if t==k:
        return [1,1]
    else:
        if evidence_list[k]=='T':
            res1, res2=backward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t,k+1)[0],backward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t,k+1)[1]
            possibility1=res1*emission_rain_umbrella
            possibility2=res2*transition_no_rain_umbrella
        else:
            res1, res2=backward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t,k+1)[0],backward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t,k+1)[1]
            possibility1=res1*(1-emission_rain_umbrella)
            possibility2=res2*(1-transition_no_rain_umbrella)
            
        value1=possibility1*transition_rain_rain+possibility2*(1-transition_rain_rain)
        value2=possibility1*transition_no_rain_rain+possibility2*(1-transition_no_rain_rain)
        norVal1=value1/(value1+value2)
        norVal2=value2/(value1+value2)
        return [norVal1,norVal2]

    
        
def smoothing(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, t, k):
    forVal=forward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, k)
    backVal=backward(evidence_list,prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t,k)
    
    
    value1=forVal[0]*backVal[0]
    value2=forVal[1]*backVal[1]
    
    norVal1=value1/(value1+value2)
    norVal2=value2/(value1+value2)
    
    return [norVal1,norVal2]

def viterbi(evidence_list, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, t):
    global hiddenStates
    global probabilities
    if t==1:
        if evidence_list[t-1]=='T':

            possibility1=prior_prob*emission_rain_umbrella
            possibility2=(1-prior_prob)*transition_no_rain_umbrella
            norVal1=possibility1/(possibility1+possibility2)
            norVal2=possibility2/(possibility1+possibility2)
            if norVal1>norVal2:
                hiddenStates.append("T")
            else:
                hiddenStates.append("F")
            probabilities.append([norVal1,norVal2])
        
            return [norVal1,norVal2]
        else:
            possibility1=prior_prob*(1-emission_rain_umbrella)
            possibility2=(1-prior_prob)*(1-transition_no_rain_umbrella)
            norVal1=possibility1/(possibility1+possibility2)
            norVal2=possibility2/(possibility1+possibility2)
            if norVal1>norVal2:
                hiddenStates.append("T")
            else:
                hiddenStates.append("F")
            probabilities.append([norVal1,norVal2])
            return [norVal1,norVal2]
    else:
        if evidence_list[t-1]=='T':
            [res1, res2]=viterbi(evidence_list, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)
            possibility1=res1*transition_rain_rain*emission_rain_umbrella
            possibility2=res2*transition_no_rain_rain*emission_rain_umbrella
            possibility3=res1*(transition_no_rain_umbrella)*(1-transition_rain_rain)
            possibility4=res2*(transition_no_rain_umbrella)*(1-transition_no_rain_rain)

            value1=max(possibility1,possibility2)
            value2=max(possibility3,possibility4)

            if value1>value2:
                hiddenStates.append("T")
            else:
                hiddenStates.append("F")
            probabilities.append([value1,value2])
            return [value1,value2]
        else:
            [res1, res2]=viterbi(evidence_list, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella,t-1)
            possibility1=res1*transition_rain_rain*(1-emission_rain_umbrella)
            possibility2=res2*transition_no_rain_rain*(1-emission_rain_umbrella)
            possibility3=res1*(1-transition_rain_rain)*(1-transition_no_rain_umbrella)
            possibility4=res2*(1-transition_no_rain_umbrella)*(1-transition_no_rain_rain)

            value1=max(possibility1,possibility2)
            value2=max(possibility3,possibility4)
            if value1>value2:
                hiddenStates.append("T")
            else:
                hiddenStates.append("F")
            probabilities.append([value1,value2])
            return [value1,value2]

            


   

        
elem=input()

args = elem.split(" ")
prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella= map(float, args[:5])
query_type = args[5]



# Process queries
if query_type == 'F':
    
    query = args[6:]
    query[0]=query[0].replace("[","")
    query[-1]=query[-1].replace("]","")
    result = forward(query, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, len(query))
    print(f"<{result[0]:.2f}, {result[1]:.2f}>")
elif query_type == 'L':
    query = args[6:]
    query[0]=query[0].replace("[","")
    query[-1]=query[-1].replace("]","")
    result = likelihood_of_evidence(query, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, len(query))
    print(f"<{result[0]+result[1]:.2f}>")
elif query_type == 'S':
    query = args[6:-1]
    query[0]=query[0].replace("[","")
    query[-1]=query[-1].replace("]","")
    k = int(args[-1])
    result = smoothing(query, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, len(query), k)
    print(f"<{result[0]:.2f}, {result[1]:.2f}>")
elif query_type == 'M':
    query = args[6:]
    query[0]=query[0].replace("[","")
    query[-1]=query[-1].replace("]","")
    result = viterbi(query, prior_prob, transition_rain_rain, transition_no_rain_rain, emission_rain_umbrella, transition_no_rain_umbrella, len(query))
    print("["+" ".join(hiddenStates)+"]", end=" ")
    for i in probabilities:
        print(f"<{i[0]:.2f}, {i[1]:.2f}>", end=" ")
        