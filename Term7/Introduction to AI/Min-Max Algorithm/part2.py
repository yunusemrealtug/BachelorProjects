import sys

OPP_MOVES_DICT={1:"U", 2:"L", 3:"R", 4:"D"}
extendedNodes=0
DEPTH=[[],[],[],[],[],[],[],[],[],[],[]]
result= {1:"+1", -1:"-1", 0:"0"}

class PuzzleNode:
    def __init__(self, state, parent=None, action=(0, 0), depth=0, player=0):
        self.state = state
        self.parent = parent
        self.action = action
        self.depth = depth
        self.player = player
        self.children=[]
        self.value=-2
        self.maxDepth=-2


def check_result (state, playerNum):
    if (state[0][0]==1 and state[0][1]==2) or (state[0][0]==2 and state[0][1]==1):
        return 1 if playerNum==1 else -1
    if (state[2][1]==8 and state[2][2]==9) or (state[2][1]==9 and state[2][2]==8):
        return 1 if playerNum==2 else -1
    else:
        return 0
    
def check_repeat (state_object, start_player):
    if state_object.parent!=None:
        if state_object.parent.parent!=None:
            if state_object.parent.parent.action[1]==state_object.action[1]:
                if state_object.action[0]+state_object.parent.parent.action[0]==5:
                    return 1 if state_object.player==start_player else -1
                    
    return 0

def check_block (state_object, start_player):
    if state_object.parent!=None:
        if state_object.parent.parent!=None:
            if state_object.parent.parent.parent!=None:
                if state_object.player==2:
                    if find_position(state_object.state, 1)==(2,1):
                        if find_position(state_object.parent.parent.state, 1)==(2,1):
                            if find_position(state_object.parent.parent.parent.state, 1)==(2,1):
                                return 1 if state_object.player==start_player else -1
                    if find_position(state_object.state, 2)==(2,1):
                        if find_position(state_object.parent.parent.state, 2)==(2,1):
                            if find_position(state_object.parent.parent.parent.state, 2)==(2,1):
                                return 1 if state_object.player==start_player else -1
                    if find_position(state_object.state, 1)==(2,2):
                        if find_position(state_object.parent.parent.state, 1)==(2,2):
                            if find_position(state_object.parent.parent.parent.state, 1)==(2,2):
                                return 1 if state_object.player==start_player else -1
                    if find_position(state_object.state, 2)==(2,2):
                        if find_position(state_object.parent.parent.state, 2)==(2,2):
                            if find_position(state_object.parent.parent.parent.state, 2)==(2,2):
                                return 1 if state_object.player==start_player else -1
                else:
                    if find_position(state_object.state, 8)==(0,1):
                        if find_position(state_object.parent.parent.state, 8)==(0,1):
                            if find_position(state_object.parent.parent.parent.state, 8)==(0,1):
                                return 1 if state_object.player==start_player else -1
                    if find_position(state_object.state, 9)==(0,1):
                        if find_position(state_object.parent.parent.state, 9)==(0,1):
                            if find_position(state_object.parent.parent.parent.state, 9)==(0,1):
                                return 1 if state_object.player==start_player else -1
                    if find_position(state_object.state, 8)==(0,0):
                        if find_position(state_object.parent.parent.state, 8)==(0,0):
                            if find_position(state_object.parent.parent.parent.state, 8)==(0,0):
                                return 1 if state_object.player==start_player else -1
                    if find_position(state_object.state, 9)==(0,0):
                        if find_position(state_object.parent.parent.state, 9)==(0,0):
                            if find_position(state_object.parent.parent.parent.state, 9)==(0,0):
                                return 1 if state_object.player==start_player else -1
    return 0




def read_initial_state_from_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()

    initial_state = [[int(num) for num in line.split()] for line in lines]
    return initial_state


def find_position(state, posNum):
    for i in range(3):
        for j in range(3):
            if state[i][j] == posNum:
                return (i, j)
            


def get_possible_moves(state, playerNum):
    possible_moves = []
    numOne=0
    numTwo=0
    if playerNum == 1:
        numOne=1
        numTwo=2
    else:
        numOne=8
        numTwo=9
    first_tile = find_position(state, numOne)
    second_tile = find_position(state, numTwo)

    if first_tile[0] > 0 and state[first_tile[0]-1][first_tile[1]] == 0:
        possible_moves.append((1, numOne))   
    if first_tile[1] < 2 and state[first_tile[0]][first_tile[1]+1] == 0:
        possible_moves.append((3, numOne))
    if first_tile[0] < 2 and state[first_tile[0]+1][first_tile[1]] == 0:
        possible_moves.append((4, numOne)) 
    if first_tile[1] > 0 and state[first_tile[0]][first_tile[1]-1] == 0:
        possible_moves.append((2, numOne))
    

    if second_tile[0] > 0 and state[second_tile[0]-1][second_tile[1]] == 0:
        possible_moves.append((1, numTwo))   
    if second_tile[1] < 2 and state[second_tile[0]][second_tile[1]+1] == 0:
        possible_moves.append((3, numTwo))
    if second_tile[0] < 2 and state[second_tile[0]+1][second_tile[1]] == 0:
        possible_moves.append((4, numTwo)) 
    if second_tile[1] > 0 and state[second_tile[0]][second_tile[1]-1] == 0:
        possible_moves.append((2, numTwo))
    
   

    return possible_moves


def apply_move(state, move):
    zero_position = find_position(state, move[1])
    new_state = [row.copy() for row in state]

    if move[0] == 1:
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0] - 1][zero_position[1]]
        new_state[zero_position[0] - 1][zero_position[1]] = move[1]
    elif move[0] == 4:
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0] + 1][zero_position[1]]
        new_state[zero_position[0] + 1][zero_position[1]] = move[1]
    elif move[0] == 2:
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0]][zero_position[1] - 1]
        new_state[zero_position[0]][zero_position[1] - 1] = move[1]
    elif move[0] == 3:
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0]][zero_position[1] + 1]
        new_state[zero_position[0]][zero_position[1] + 1] = move[1]

    return new_state

def create_tree(state_object, start_player, current_player):
    stateCheck=check_result(state_object.state, start_player) 
    repeatCheck=check_repeat(state_object, start_player)
    blockCheck=check_block(state_object, start_player)
    if state_object.depth<10:
        if stateCheck ==0:
            if repeatCheck==0:
                if blockCheck==0:
                    for move in get_possible_moves(state_object.state, current_player):
                        child_Object=PuzzleNode(apply_move(state_object.state, move), state_object, move, state_object.depth+1, 3-current_player)
                        state_object.children.append(child_Object)
                        create_tree(child_Object, start_player, 3-current_player)
                else:
                    state_object.value=blockCheck
                    state_object.maxDepth=state_object.depth
            else:
                state_object.value=repeatCheck
                state_object.maxDepth=state_object.depth
        else:
            state_object.value=stateCheck
            state_object.maxDepth=state_object.depth
    else:
        state_object.maxDepth=state_object.depth
        if stateCheck==0:
            if repeatCheck==0:
                if blockCheck==0:
                    state_object.value=0
                    state_object.maxDepth=0
                else:
                    state_object.value=blockCheck
            else:
                state_object.value=repeatCheck
        else:
            state_object.value=stateCheck

##everything above this line is the same as part1.py   

def minMaxDecision (state_object, start_player, alpha, beta):
    global extendedNodes
    extendedNodes+=1
    DEPTH[state_object.depth].append(state_object)

    if state_object.value!=-2:
        return state_object.value
    
    if state_object.player==start_player:
        bestVal=-2
        for child in state_object.children:
            val=minMaxDecision(child, start_player, alpha, beta)
            bestVal=max(bestVal, val)
            if beta <= bestVal:
                return bestVal
            alpha=max(alpha, bestVal)
        return bestVal
    else:
        bestVal=2
        for child in state_object.children:
            val=minMaxDecision(child, start_player, alpha, beta)
            bestVal=min(bestVal, val)
            if bestVal <= alpha:
                return bestVal
            beta = min( beta, bestVal)
        return bestVal
##we are implementing pruning here.
 
if __name__ == "__main__":

    startingPlayer = int(sys.argv[1])
    input_file_path = sys.argv[2]
    output_file_path = sys.argv[3]
    initial_state = read_initial_state_from_file(input_file_path)
    root = PuzzleNode(initial_state, None, (0,0), 0, startingPlayer)
    
    create_tree(root, startingPlayer, startingPlayer)
    val=minMaxDecision(root, startingPlayer,-2,2)
    output_file = open(output_file_path, 'w')
    output_file.write(f"{result[val]}\n")
    output_file.write(f"{extendedNodes}\n")
    output_file.close()
    
    