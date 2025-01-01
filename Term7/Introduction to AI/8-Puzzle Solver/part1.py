import heapq
import sys

NUMTOACT = {0: "None", 1:"U", 2:"R", 3:"D", 4:"L"}
ACTTONUM = {"None": 0, "U":1, "R":2, "D":3, "L":4}

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
        heapq.heappush(self._queue, (task.aStar, task.actionNum, task.fringeIndex, self._index, task))
        self._index += 1

    def pop(self):
        return heapq.heappop(self._queue)[-1]




def read_initial_state_from_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()

    initial_state = [[int(num) for num in line.split()] for line in lines]
    return initial_state

def dfs(initial_state, goal_state):
    stack = [PuzzleNode(initial_state)]
    visited = set()
    node_count = 0

    while stack:
        current_node = stack.pop()
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
            continue  

        visited.add(state_tuple)

        next_moves = get_possible_moves_dfs(current_state)

        for move in next_moves:
            new_state = apply_move(current_state, move)
            new_node = PuzzleNode(new_state, current_node, move)
            stack.append(new_node)

    return None, 0  

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
            continue 

        visited.add(state_tuple)

        next_moves = get_possible_moves_bfs(current_state)

        for move in next_moves:
            new_state = apply_move(current_state, move)
            new_node = PuzzleNode(new_state, current_node, move)
            stack.append(new_node)

    return None, 0  

def ucs(initial_state, goal_state):
    
    priority_queue = PriorityQueue()
    init=PuzzleNode(initial_state)
    priority_queue.pushUcs(init)
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
            continue  

        visited.add(state_tuple)

        next_moves = get_possible_moves_bfs(current_state)

        for move in next_moves:
            new_state = apply_move(current_state, move)
            new_node = PuzzleNode(new_state, current_node, move, calcManh(new_state))
            fringeIndex+=1
            new_node.fringeIndex=fringeIndex
            priority_queue.pushUcs(new_node)

    return None, 0 


def greedy(initial_state, goal_state):
    
    priority_queue = PriorityQueue()
    init=PuzzleNode(initial_state)
    init.greed=calcManh(initial_state)
    priority_queue.pushGreed(init)
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
            continue  

        visited.add(state_tuple)

        next_moves = get_possible_moves_bfs(current_state)

        for move in next_moves:
            new_state = apply_move(current_state, move)
            new_node = PuzzleNode(new_state, current_node, move, calcManh(new_state))
            fringeIndex+=1
            new_node.fringeIndex=fringeIndex
            priority_queue.pushGreed(new_node)

    return None, 0 

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
            continue  

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

def get_possible_moves_dfs(state):
    zero_position = find_zero_position(state)
    possible_moves = []

    if zero_position[1] > 0:
        possible_moves.append("L")
    if zero_position[0] < 2:
        possible_moves.append("D")
    if zero_position[1] < 2:
        possible_moves.append("R")
    if zero_position[0] > 0:
        possible_moves.append("U")

    return possible_moves

def get_possible_moves_bfs(state):
    zero_position = find_zero_position(state)
    possible_moves = []

    if zero_position[0] > 0:
        possible_moves.append("U")
    if zero_position[1] < 2:
        possible_moves.append("R")
    if zero_position[0] < 2:
        possible_moves.append("D")
    if zero_position[1] > 0:
        possible_moves.append("L")
    
    
    

    return possible_moves

def calcManh (state):
    total=0
    for i in range(3):
        for j in range(3):
            if state[i][j]!=0:
                total+=abs(i-(state[i][j]-1)//3)+abs(j-(state[i][j]-1)%3)
    return total

def apply_move(state, move):
    zero_position = find_zero_position(state)
    new_state = [row.copy() for row in state]

    if move == "U":
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0] - 1][zero_position[1]]
        new_state[zero_position[0] - 1][zero_position[1]] = 0
    elif move == "D":
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0] + 1][zero_position[1]]
        new_state[zero_position[0] + 1][zero_position[1]] = 0
    elif move == "L":
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0]][zero_position[1] - 1]
        new_state[zero_position[0]][zero_position[1] - 1] = 0
    elif move == "R":
        new_state[zero_position[0]][zero_position[1]] = new_state[zero_position[0]][zero_position[1] + 1]
        new_state[zero_position[0]][zero_position[1] + 1] = 0

    return new_state

def find_zero_position(state):
    for i in range(3):
        for j in range(3):
            if state[i][j] == 0:
                return (i, j)

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
    goal_state = [[1, 2, 3], [4, 5, 6], [7, 8, 0]]

    solution_path, node_count = dfs(initial_state, goal_state)
    solution_path2, node_count2 = bfs(initial_state, goal_state)
    solution_path3, node_count3 = ucs(initial_state, goal_state)
    solution_path4, node_count4 = greedy(initial_state, goal_state)
    solution_path5, node_count5 = aStar(initial_state, goal_state)
    output_file = open(output_file_path, 'w')

    if solution_path2:
        output_file.write(f"{node_count2}\n")
        output_file.write(f"{len(solution_path2)-1}\n")
        for i in range(len(solution_path2) - 1):
            output_file.write(f"{solution_path2[i + 1][1]} ")
        output_file.write(f"\n")
        
        
    else:
        output_file.write("No solution found.\n")

    if solution_path:
        output_file.write(f"{node_count}\n")
        output_file.write(f"{len(solution_path)-1}\n")
        for i in range(len(solution_path) - 1):
            output_file.write(f"{solution_path[i + 1][1]} ")
        output_file.write(f"\n")
        
    else:
        output_file.write("No solution found.\n")
        
    if solution_path3:
        output_file.write(f"{node_count3}\n")
        output_file.write(f"{len(solution_path3)-1}\n")
        for i in range(len(solution_path3) - 1):
            output_file.write(f"{solution_path3[i + 1][1]} ")
        output_file.write(f"\n")
        
        
    else:
        output_file.write("No solution found.\n")
    
    if solution_path4:
        output_file.write(f"{node_count4}\n")
        output_file.write(f"{len(solution_path4)-1}\n")
        for i in range(len(solution_path4) - 1):
            output_file.write(f"{solution_path4[i + 1][1]} ")
        output_file.write(f"\n")
        
        
    else:
        output_file.write("No solution found.\n")

    if solution_path5:
        output_file.write(f"{node_count5}\n")
        output_file.write(f"{len(solution_path5)-1}\n")
        for i in range(len(solution_path5) - 1):
            output_file.write(f"{solution_path5[i + 1][1]} ")
        output_file.write(f"\n")
        
        
    else:
        output_file.write("No solution found.\n")


    output_file.close()

    

