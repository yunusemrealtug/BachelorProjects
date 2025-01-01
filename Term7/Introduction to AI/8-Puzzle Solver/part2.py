import heapq
import sys

NUMTOACT = {0: "None", 1:"U", 2:"R", 3:"D", 4:"L"}
ACTTONUM = {"None": 0, "U10":1, "U11":2, "U12":3, "U20":4, "U21":5, "U22":6,
            "R00":7, "R01":8, "R10":9, "R11":10, "R20":11, "R21":12,
            "D00":13, "D01":14, "D02":15, "D10":16, "D11":17, "D12":18,
            "L01":19, "L02":20, "L11":21, "L12":22, "L21":23, "L22":24,
            }

class PuzzleNode:
    def __init__(self, state, parent=None, action="None", greed=0):
        self.state = state
        self.parent = parent
        self.action = action
        if parent!=None:
            self.cost=parent.cost+1
        else:
            self.cost=0
        self.greed=greed
        self.actionNum=ACTTONUM[action]
        self.fringeIndex=0
        self.aStar=0

class PriorityQueue:
    def __init__(self):
        self._queue = []
        self._index = 0

    def pushGreed(self, task):
        heapq.heappush(self._queue, (task.greed, task.cost,task.actionNum, task.fringeIndex, self._index, task))
        self._index += 1

    def pushUcs(self, task):
        heapq.heappush(self._queue, (task.cost, task.actionNum, task.fringeIndex, self._index, task))
        self._index += 1
    
    def pushAStar(self, task):
        heapq.heappush(self._queue, (task.aStar, task.greed, task.cost, task.actionNum, task.fringeIndex, self._index, task))
        self._index += 1

    def pop(self):
        return heapq.heappop(self._queue)[-1]




def read_initial_state_from_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()

    initial_state = [[int(num) for num in line.split()] for line in lines]
    return initial_state


def bfs(initial_state, goal_state):
    stack = [PuzzleNode(initial_state)]
    visited = set()
    node_count = 0

    while stack:
        current_node = stack.pop(0)
        current_state = current_node.state

        if current_state == goal_state:
            while stack:
                current_node2 = stack.pop()
                current_state2 = current_node2.state
                state_tuple = tuple(map(tuple, current_state2))
                if state_tuple not in visited:
                    visited.add(state_tuple)
            node_count=len(visited)
            return get_solution_path(current_node), node_count

        state_tuple = tuple(map(tuple, current_state))
        if state_tuple in visited:
            continue  # Skip if the state has been visited before

        visited.add(state_tuple)

        next_moves = get_possible_moves_bfs(current_state)

        for move in next_moves:
            new_state = apply_move(current_state, move)
            new_node = PuzzleNode(new_state, current_node, move)
            stack.append(new_node)

    return None, 0  # No solution found


def aStar(initial_state, goal_state):
    
    priority_queue = PriorityQueue()
    init=PuzzleNode(initial_state)
    init.greed=calcManh(initial_state)
    init.aStar=init.greed
    priority_queue.pushAStar(init)
    visited = set()
    node_count = 0
    fringeIndex=0

    while len(priority_queue._queue) > 0:
        poppedState = priority_queue.pop()
        current_node = poppedState
        current_state = current_node.state

        if current_state == goal_state:
            while len(priority_queue._queue) > 0:
                current_node2 = priority_queue.pop()
                current_state2 = current_node2.state
                state_tuple = tuple(map(tuple, current_state2))
                if state_tuple not in visited:
                    visited.add(state_tuple)
            node_count=len(visited)
            return get_solution_path(current_node), node_count

        state_tuple = tuple(map(tuple, current_state))
        if state_tuple in visited:
            continue  # Skip if the state has been visited before

        visited.add(state_tuple)

        next_moves = get_possible_moves_bfs(current_state)

        for move in next_moves:
            new_state = apply_move(current_state, move)
            new_node = PuzzleNode(new_state, current_node, move, calcManh(new_state))
            fringeIndex+=1
            new_node.fringeIndex=fringeIndex
            new_node.aStar=new_node.greed+new_node.cost
            priority_queue.pushAStar(new_node)

    return None, 0  # No solution found

def get_possible_moves_bfs(state):
    zero_positions = find_zero_position(state)
    possible_moves = []

    for i in range(3):
        if zero_positions[i][0] > 0 and state[zero_positions[i][0]-1]!=0:
            possible_moves.append("U"+str(zero_positions[i][0])+str(zero_positions[i][1]))
        if zero_positions[i][1] < 2 and state[zero_positions[i][1]+1]!=0:
            possible_moves.append("R"+str(zero_positions[i][0])+str(zero_positions[i][1]))
        if zero_positions[i][0] < 2 and state[zero_positions[i][0]+1]!=0:
            possible_moves.append("D"+str(zero_positions[i][0])+str(zero_positions[i][1]))
        if zero_positions[i][1] > 0 and state[zero_positions[i][1]-1]!=0:
            possible_moves.append("L"+str(zero_positions[i][0])+str(zero_positions[i][1]))
    
    
    

    return possible_moves

def calcManh (state):
    total=0
    for i in range(3):
        for j in range(3):
            if state[i][j]!=0:
                total+=abs(i-(state[i][j]-1)//3)+abs(j-(state[i][j]-1)%3)
    
    lastNum=0
    for i in range(3):
        if state[0][i]==1 or state[0][i]==3 or state[0][i]==2:
            if state[0][i]-lastNum<0:
                total+=2
            lastNum=state[0][i]
    lastNum=0
    for i in range(3):
        if state[1][i]==4 or state[1][i]==5 or state[1][i]==6:
            if state[1][i]-lastNum<0:
                total+=2
            lastNum=state[1][i]

    lastNum=0
    for i in range(3):
        if state[i][0]==1 or state[i][0]==4:
            if state[i][0]-lastNum<0:
                total+=2
            lastNum=state[i][0]

    lastNum=0
    for i in range(3):
        if state[i][1]==2 or state[i][1]==5:
            if state[i][1]-lastNum<0:
                total+=2
            lastNum=state[i][1]
    lastNum=0
    for i in range(3):
        if state[i][2]==3 or state[i][2]==6:
            if state[i][2]-lastNum<0:
                total+=2
            lastNum=state[i][2]


    return total

def apply_move(state, move):
    new_state = [row.copy() for row in state]

    if move[0] == 'U':
        new_state[int(move[1])][int(move[2])] = new_state[int(move[1]) - 1][int(move[2])]
        new_state[int(move[1]) - 1][int(move[2])] = 0
    elif move[0] == 'D':
        new_state[int(move[1])][int(move[2])] = new_state[int(move[1]) + 1][int(move[2])]
        new_state[int(move[1]) + 1][int(move[2])] = 0
    elif move[0] == 'L':
        new_state[int(move[1])][int(move[2])] = new_state[int(move[1])][int(move[2]) - 1]
        new_state[int(move[1])][int(move[2]) - 1] = 0
    elif move[0] == 'R':
        new_state[int(move[1])][int(move[2])] = new_state[int(move[1])][int(move[2]) + 1]
        new_state[int(move[1])][int(move[2]) + 1] = 0

    return new_state

def find_zero_position(state):
    zeroList=[]
    for i in range(3):
        for j in range(3):
            if state[i][j] == 0:
                zeroList.append((i,j))
    return zeroList

def get_solution_path(node):
    path = []
    while node:
        path.append((node.state, node.action))
        node = node.parent
    return list(reversed(path))

if __name__ == "__main__":
    
    input_file_path = sys.argv[1]
    output_file_path = sys.argv[2]

    initial_state = read_initial_state_from_file(input_file_path)
    goal_state = [[1, 2, 3], [4, 5, 6], [0, 0, 0]]

    solution_path5, node_count5 = aStar(initial_state, goal_state)
    output_file = open(output_file_path, 'w')
    

    if solution_path5:
        output_file.write(f"{node_count5}\n")
        output_file.write(f"{len(solution_path5)-1}\n")
        for i in range(len(solution_path5) - 1):
            for j in range(3):
                output_file.write(f"{solution_path5[i][0][j]}\n")
            output_file.write(f"{solution_path5[i + 1][1]}\n")
        output_file.write(f"\n")
        
        
    else:
        output_file.write("No solution found.\n")


    output_file.close()

    

