:- ['cmpecraft.pro'].

:- init_from_map.

%THIS FUNCTION IS MADE FOR FIND_DISTANCE WHICH IS HELPER FUNCTION OF FIND_NEAREST_TYPE.
%less(+ObjectKey1,+Object1,+Distance1,+ObjectKey2,+Object2,+Distance2,-CloserObjectKey,-CloserObject,-LessDistance)
less(A,B,C,D,E,F,G,H,I):-C<F, G is A, B=H, I is C,!.    % Purpose of this function is comparing the distances of two objects,
less(A,B,C,D,E,F,G,H,I):-C>=F, G is D, E=H, I is F.     % C and F are distances, A and D are object keys, B and E are objects; 
                                                        % G, H, I are basically are features of object which is closer.

manhattan_distance([H,T],[I,U], Distance):- Distance is abs(I-H)+abs(T-U). 


minimum_of_list([X], X):- !.
minimum_of_list([H|T], D):- minimum_of_list(T, E), E<H, D is E, !.
minimum_of_list([H|T], D):- minimum_of_list(T, E), E>=H, D is H.

%THIS FUNCTION IS MADE FOR FIND_NEAREST_TYPE.
%find_distance(+ObjectDict, +TypeArray, +CoordinateAgent,ClosestObjectKey,ClosestObject,LeastDistance)
find_distance(Dict, [H],Agent,Key,Value, Distance2):- % Dict is ObjectDict, array is array of objects with a spesific type,
    get_dict(H, Dict, Val),                           % Agent is the coordinates of agent. In base condition, features of the only object
    get_dict(x, Val, Xval),                           % are returned.
    get_dict(y, Val, Yval),
    manhattan_distance(Agent,[Xval,Yval], Distance), 
    Key is H,
    Value=Val,
    Distance2 is Distance, !.
find_distance(Dict, [H|T],Agent,Key,Value, Distance2):- % Recursively we get closest object by comparing closest until having head, and head. 
    find_distance(Dict, T,Agent,Key1,Value1, Distance3),
    get_dict(H, Dict, Val), 
    get_dict(x, Val, Xval), 
    get_dict(y, Val, Yval),
    manhattan_distance(Agent,[Xval,Yval], Distance), 
    less(Key1,Value1,Distance3,H,Val,Distance, R1,R2,R3),
    Key is R1,
    Value=R2,
    Distance2 is R3.


find_nearest_type([X,Y,Z], A, B, C ,D):- % In this function X is AgentDict, Y is ObjectDict, Z is  time,  
    get_dict(x, X, Ax),                  % A is requested type,B C and D results.
    get_dict(y, X, Ay),                  % We have already found results in find_distance.
    findall(Object, (get_dict(Object, Y, Value), get_dict(type,Value, A)), Results),
	find_distance(Y,Results,[Ax,Ay],Key,Value1,Distance),
    B is Key,
    C=Value1,
	D is Distance.

%THIS FUNCTION IS MADE FOR NAVIGATE_TO.
%x_move(+ActualAgentXCoordinate,+XCoordinate2, -ActionList) (this function is made before understanding if :))
x_move(X1,X2,List):-    % This is the base condition, if x1 equals x2 there is no movement.
    X1==X2,
	List=[], !.
	    
x_move(X1,X2,List):-    % If agent is in right of X2, goes left. Actually object goes right but, I do not need to change, :)
    X1>X2,              % which does not affect the result. Movement is being appended to actionslist.
    New_x is X2+1,
    x_move(X1, New_x, T),
    List=[go_left|T], !.
	
x_move(X1,X2,List):-
    X1<X2,
    New_x is X2-1,
    x_move(X1, New_x, T),
    List=[go_right|T].
%THIS FUNCTION IS MADE FOR NAVIGATE_TO.
%y_move(+ActualAgentYCoordinate,+YCoordinate2,+XList -ActionList) (this function is made before understanding if :))
y_move(Y1,Y2,List,LastList):- % Same with x_move. However, I first implement x_move, so I need to append vertical movement to 
    Y1==Y2,                   % actionlist of x_move. Therefore, y_move takes four parameters. XList is  list of horizontal movement. 
	LastList=List,!.

	    
y_move(Y1,Y2,List,LastList):-
    Y1>Y2,
    New_y is Y2+1,
    y_move(Y1, New_y,List, T),
    LastList=[go_up|T], !.
	
y_move(Y1,Y2,List,LastList):-
    Y1<Y2,
    New_y is Y2-1,
    y_move(Y1, New_y, List, T),
    LastList=[go_down|T].
	
navigate_to([X,Y,Z], A, B, ActionList ,DepthLimit):- %Thanks to x and y_move functions I get ActionsList.
    get_dict(x, X, Ax),
    get_dict(y, X, Ay),
    manhattan_distance([Ax,Ay],[A,B], Distance),
    Distance=<DepthLimit,
	x_move(Ax,A,List),
    y_move(Ay,B,List,LastList),
	ActionList=LastList.
    

chop_nearest_tree([X,Y,Z], A):-
	find_nearest_type([X,Y,Z], tree, B, C ,D),
    get_dict(x, C, Ax),
	get_dict(y, C, Ay),
    navigate_to([X,Y,Z], Ax, Ay, ActionList ,9999999999),
    append(ActionList,[left_click_c,left_click_c,left_click_c,left_click_c],Res),
    A=Res.


mine_nearest_stone([X,Y,Z], A):-
    find_nearest_type([X,Y,Z], stone, B, C ,D),
    get_dict(x, C, Ax),
    get_dict(y, C, Ay),
    navigate_to([X,Y,Z], Ax, Ay, ActionList ,9999999999),
    append(ActionList,[left_click_c,left_click_c,left_click_c,left_click_c],Res),
    A=Res.

%I considered mining cobbstone in collect_requirements. This function mines 3 cobblestones.
mine_nearest_cobbstone([X,Y,Z], A):-
    find_nearest_type([X,Y,Z], cobblestone, B, C ,D),
    get_dict(x, C, Ax),
    get_dict(y, C, Ay),
    navigate_to([X,Y,Z], Ax, Ay, ActionList ,9999999999),
    append(ActionList,[left_click_c,left_click_c,left_click_c,left_click_c],Res),
    execute_actions([X,Y,Z], Res, Next1),
    find_nearest_type(Next1, cobblestone, E, F ,G),
    get_dict(x, F, Axf),
    get_dict(y, F, Ayf),
    navigate_to(Next1, Axf, Ayf, ActionList1,9999999999),
    append(ActionList1,[left_click_c,left_click_c,left_click_c,left_click_c],Res1),
    execute_actions(Next1, Res1, Next2),
    find_nearest_type(Next2, cobblestone, H, I ,J),
    get_dict(x, I, Axi),
    get_dict(y, I, Ayi),
    navigate_to(Next2, Axi, Ayi, ActionList2,9999999999),
    append(ActionList2,[left_click_c,left_click_c,left_click_c,left_click_c],Res2),
    append(Res,Res1,Res3),
    append(Res3,Res2,Res4),
    A=Res4.

gather_nearest_food([X,Y,Z], A):-
    find_nearest_type([X,Y,Z], food, B, C ,D),
    get_dict(x, C, Ax),
    get_dict(y, C, Ay),
    navigate_to([X,Y,Z], Ax, Ay, ActionList ,9999999999),
    append(ActionList,[left_click_c],Res),
    A=Res.


collect_requirements([X,Y,Z], stick, A) :- 
    get_dict(inventory, X, Inv),
    (has(log, 2, Inv) ->A=[];
    chop_nearest_tree([X,Y,Z], A)).

% This function checks firstly, requirement of stick, then checks log and then cobblestone. If stone cannot be mined,
% we are trying cobblestone.
% If there are enough stick in bag, Sticklist is empty,
% else if there are enough log for stick, Sticklist is [craft_stick],
% else we are chopping tree and crafting, Sticklist consists of ActionList of chopping and [craft_stick].
% If there are enough log in bag, LogList is empty, otherwise Loglist .
% If logs are more than 4 or (sticks are more than 1 and logs are more than 2) LogList is empty,
% Loglist are not actions of getting log for stick, just for getting log for stone_pickaxe.
% Cobblist is list of actions for mining stone or mining cobblestone

collect_requirements([X,Y,Z], stone_pickaxe, A) :- 
    get_dict(inventory, X, Inv), 
    %IF 1                  
    (has(stick, 2, Inv) ->
        %IF 2
    	Sticklist=[], (has(log, 3, Inv)->
            %IF 3
        	Loglist=[], (has(cobblestone, 3, Inv)->	
            	Cobblist=[];
                % ELSE 3
                % If there is no stone, agent tries to mine cobblestone. IF 4.
    			(mine_nearest_stone([X,Y,Z], Cobblist)->
                	mine_nearest_stone([X,Y,Z], Cobblist);
                    %ELSE 4
    				mine_nearest_cobbstone([X,Y,Z], Cobblist)));
            % We have sticks, but not enough logs, so chopping is required. ELSE 2
  			chop_nearest_tree([X,Y,Z], Loglist),
    		execute_actions([X,Y,Z], Loglist, [X1,Y1,Z1]), (has(cobblestone, 3, Inv)->	
            	Cobblist=[];
    			(mine_nearest_stone([X1,Y1,Z1], Cobblist)->
                	mine_nearest_stone([X1,Y1,Z1], Cobblist);
    				mine_nearest_cobbstone([X1,Y1,Z1], Cobblist))));
        % We do not have stick, so we should look at logs. ELSE 1     
        % If number of logs are more than 4, no need to collect log. IF 5      
    	(has(log, 5, Inv)->  
        	Sticklist=[craft_stick], Loglist=[], (has(cobblestone, 3, Inv)->	
            	Cobblist=[];
    			(mine_nearest_stone([X,Y,Z], Cobblist)->mine_nearest_stone([X,Y,Z], Cobblist);
    			mine_nearest_cobbstone([X,Y,Z], Cobblist)));
            %We do not have 5 logs but if we have 2, no need to chop two times. ELSE 5, IF 6
    		(has(log, 2, Inv)->  
            	Sticklist=[craft_stick], chop_nearest_tree([X,Y,Z], Loglist),
                execute_actions([X,Y,Z], Loglist, [X1,Y1,Z1]),
            	(has(cobblestone, 3, Inv)->	
            		Cobblist=[];
    				(mine_nearest_stone([X1,Y1,Z1], Cobblist)->
                		mine_nearest_stone([X1,Y1,Z1], Cobblist);
    					mine_nearest_cobbstone([X1,Y1,Z1], Cobblist)));
                % We do not have even 2 logs, so need to chop two times ELSE 6
    			chop_nearest_tree([X,Y,Z], Actionstick),
    			append(Actionstick,[craft_stick],Sticklist),
   				execute_actions([X,Y,Z], Sticklist, [X1,Y1,Z1]),
    			chop_nearest_tree([X1,Y1,Z1], Loglist),
            	execute_actions([X1,Y1,Z1], Loglist, [X2,Y2,Z2]),
            	(has(cobblestone, 3, Inv)->	
            		Cobblist=[];
    				(mine_nearest_stone([X2,Y2,Z2], Cobblist)->
                		mine_nearest_stone([X2,Y2,Z2], Cobblist);
    					mine_nearest_cobbstone([X2,Y2,Z2], Cobblist)))))),
    append(Sticklist, Loglist, NewList),
    append(NewList, Cobblist, A).


collect_requirements([X,Y,Z], stone_axe, A) :-
    get_dict(inventory, X, Inv),
    (has(stick, 2, Inv) ->
    	Sticklist=[], (has(log, 3, Inv)->
        	Loglist=[], (has(cobblestone, 3, Inv)->	
            	Cobblist=[];
    			(mine_nearest_stone([X,Y,Z], Cobblist)->
                	mine_nearest_stone([X,Y,Z], Cobblist);
    				mine_nearest_cobbstone([X,Y,Z], Cobblist)));
  			chop_nearest_tree([X,Y,Z], Loglist),
    		execute_actions([X,Y,Z], Loglist, [X1,Y1,Z1]), (has(cobblestone, 3, Inv)->	
            	Cobblist=[];
    			(mine_nearest_stone([X1,Y1,Z1], Cobblist)->
                	mine_nearest_stone([X1,Y1,Z1], Cobblist);
    				mine_nearest_cobbstone([X1,Y1,Z1], Cobblist))));
    	(has(log, 5, Inv)->  
        	Sticklist=[craft_stick], Loglist=[], (has(cobblestone, 3, Inv)->	
            	Cobblist=[];
    			(mine_nearest_stone([X,Y,Z], Cobblist)->mine_nearest_stone([X,Y,Z], Cobblist);
    			mine_nearest_cobbstone([X,Y,Z], Cobblist)));
    		(has(log, 2, Inv)->  
            	Sticklist=[craft_stick], chop_nearest_tree([X,Y,Z], Loglist),
                execute_actions([X,Y,Z], Loglist, [X1,Y1,Z1]),
            	(has(cobblestone, 3, Inv)->	
            		Cobblist=[];
    				(mine_nearest_stone([X1,Y1,Z1], Cobblist)->
                		mine_nearest_stone([X1,Y1,Z1], Cobblist);
    					mine_nearest_cobbstone([X1,Y1,Z1], Cobblist)));
    			chop_nearest_tree([X,Y,Z], Actionstick),
    			append(Actionstick,[craft_stick],Sticklist),
   				execute_actions([X,Y,Z], Sticklist, [X1,Y1,Z1]),
    			chop_nearest_tree([X1,Y1,Z1], Loglist),
            	execute_actions([X1,Y1,Z1], Loglist, [X2,Y2,Z2]),
            	(has(cobblestone, 3, Inv)->	
            		Cobblist=[];
    				(mine_nearest_stone([X2,Y2,Z2], Cobblist)->
                		mine_nearest_stone([X2,Y2,Z2], Cobblist);
    					mine_nearest_cobbstone([X2,Y2,Z2], Cobblist)))))),
    append(Sticklist, Loglist, NewList),
    append(NewList, Cobblist, A).


%THIS FUNCTION IS MADE FOR SQUARE_OCC, WHICH IS HELPER OF SQUARE, WHICH IS HELPER OF FIND_CASTLE_LOCATION.
%tile_occ(+XCoordinate, +YCoordinate, +State)
tile_occ(X, Y, [A,B,C]) :-      % X and Y are coordinates, array is state array. Function checks point is empty or not.
    get_dict(_, B, Object),
    get_dict(x, Object, X),
    get_dict(y, Object, Y),!.
%THIS FUNCTION IS MADE FOR SQUARE, WHICH IS HELPER OF FIND_CASTLE_LOCATION.
%square_occ(+XCoordinate, +YCoordinate, +State)
square_occ(X, Y, [A,B,C]):-     % X and Y are coordinates, array is state array. Function checks (X,Y),(X+1,Y), (X+2, Y), (X,Y+1)..
    X1 is X+1,                  % If all 9 points are empty returns true.
    X2 is X1+1,
    Y1 is Y+1,
    Y2 is Y1+1,
    not(tile_occ(X, Y, [A,B,C])),
    not(tile_occ(X1, Y, [A,B,C])),
    not(tile_occ(X2, Y, [A,B,C])),
    not(tile_occ(X, Y1, [A,B,C])),
    not(tile_occ(X1, Y1, [A,B,C])),
    not(tile_occ(X2, Y1, [A,B,C])),
    not(tile_occ(X, Y2, [A,B,C])),
    not(tile_occ(X1, Y2, [A,B,C])),
    not(tile_occ(X2, Y2, [A,B,C])).


%THIS FUNCTION IS MADE FOR FIND_CASTLE_LOCATION.
%square(+XCoordinate, +YCoordinate, +State, -X,Y-)
square(X, Y, [A,B,C],XI,YI):-   % This function checks points, which can be left top point of 3x3 square until success.
    X1 is X+1,                  % If there is no returns false.
    X4 is X1+3,
    Y1 is Y+1,
    Y4 is Y1+3,
    ((width(X4), height(Y4))->square_occ(X, Y, [A,B,C]), XI is X, YI is Y;
    (width(X4)-> 
    	(square_occ(X, Y, [A,B,C])-> 
    		XI is X, YI is Y;
    		square(1,Y1, [A,B,C],XI,YI));
    	(square_occ(X, Y, [A,B,C])-> XI is X, YI is Y;
    	square(X1,Y, [A,B,C],XI,YI)))).
    


find_castle_location([A,B,C],XMin,YMin,XMax,YMax):-     % Width and height have to be greater than 4, we check with square func and return.
    width(Width),                   
    height(Height),
    Height > 4,
    Width >  4,
    square(1, 1, [A,B,C],XI,YI),
    XMin is XI,
    XMax is XI+2,
    YMin is YI,
    YMax is YI+2.


make_castle([X,Y,Z], ActionList):-
    get_dict(inventory, X, Inv),
    (has(cobblestone, 9, Inv)-> 
    %If we have enough cobblestone in inventory, I do not need to try collect, and I look for a castle location. 
    find_castle_location([X,Y,Z],XMin,YMin,XMax,YMax),
    % Navigating to left top of square.
	navigate_to([X,Y,Z], XMin, YMin, PreAction ,9999999999999),
    % Placing cobbs into ground to make castle.
    append(PreAction,[place_c,go_right,place_c,go_right,place_c,go_down,
                       place_c,go_left,place_c,go_left,place_c,go_down,
                       place_c,go_right,place_c,go_right,place_c],ActionList);
    %We have more than 6 but less than 9, so 1 stone or 3 cobbs are enough.                   
    (has(cobblestone, 6, Inv)-> 
    (mine_nearest_stone([X,Y,Z], Act)->
                	mine_nearest_stone([X,Y,Z], Act);
    				mine_nearest_cobbstone([X,Y,Z], Act)),
    execute_actions([X,Y,Z], Act, [X1,Y1,Z1]),
    find_castle_location([X1,Y1,Z1],XMin,YMin,XMax,YMax),
	navigate_to([X1,Y1,Z1], XMin, YMin, PreAction ,9999999999999),
    append(Act,PreAction, NPreAction),
    append(NPreAction,[place_c,go_right,place_c,go_right,place_c,go_down,
                       place_c,go_left,place_c,go_left,place_c,go_down,
                       place_c,go_right,place_c,go_right,place_c],ActionList);
    (has(cobblestone, 3, Inv)-> 
    (mine_nearest_stone([X,Y,Z], Act)->
                	mine_nearest_stone([X,Y,Z], Act);
    				mine_nearest_cobbstone([X,Y,Z], Act)),
    execute_actions([X,Y,Z], Act, [X2,Y2,Z2]),
    (mine_nearest_stone([X2,Y2,Z2], Act1)->
                	mine_nearest_stone([X2,Y2,Z2], Act1);
    				mine_nearest_cobbstone([X2,Y2,Z2], Act1)),
    execute_actions([X2,Y2,Z2], Act1, [X1,Y1,Z1]),
    find_castle_location([X1,Y1,Z1],XMin,YMin,XMax,YMax),
	navigate_to([X1,Y1,Z1], XMin, YMin, PreAction ,9999999999999),
    append(Act,Act1, Act2),
    append(Act2,PreAction, NPreAction),
    append(NPreAction,[place_c,go_right,place_c,go_right,place_c,go_down,
                       place_c,go_left,place_c,go_left,place_c,go_down,
                       place_c,go_right,place_c,go_right,place_c],ActionList);
    % We need to collect 3 stone, 9 cobbstone.
    (mine_nearest_stone([X,Y,Z], Act)->
                	mine_nearest_stone([X,Y,Z], Act);
    				mine_nearest_cobbstone([X,Y,Z], Act)),
    execute_actions([X,Y,Z], Act, [X2,Y2,Z2]),
    (mine_nearest_stone([X2,Y2,Z2], Act1)->
                	mine_nearest_stone([X2,Y2,Z2], Act1);
    				mine_nearest_cobbstone([X2,Y2,Z2], Act1)),
    execute_actions([X2,Y2,Z2], Act1, [X3,Y3,Z3]),
    (mine_nearest_stone([X3,Y3,Z3], Act2)->
                	mine_nearest_stone([X3,Y3,Z3], Act2);
    				mine_nearest_cobbstone([X3,Y3,Z3], Act2)),
    execute_actions([X3,Y3,Z3], Act2, [X1,Y1,Z1]),
    find_castle_location([X1,Y1,Z1],XMin,YMin,XMax,YMax),
	navigate_to([X1,Y1,Z1], XMin, YMin, PreAction ,9999999999999),
    append(Act,Act1, Act3),
    append(Act3,Act2, Act4),
    append(Act4,PreAction, NPreAction),
    append(NPreAction,[place_c,go_right,place_c,go_right,place_c,go_down,
                       place_c,go_left,place_c,go_left,place_c,go_down,
                       place_c,go_right,place_c,go_right,place_c],ActionList)
    )
    )).

