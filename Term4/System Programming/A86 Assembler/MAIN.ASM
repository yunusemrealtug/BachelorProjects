
jmp start
;=====================
; variables
;=====================
; 5 dup 0 = 0 0 0 0 0
	number db 5 dup 0
	temp   dw 0
	n1     dw 0			
;===================s

start:
	mov cx,0	 			; cx will hold the current integer
;=====================
; read characters
;=====================

morechar:
	mov ah,01h				; reads a character to al
	int 21h					
	mov dx,0				; dx becomes 0 
	mov dl,al				; store the input character in dl
	mov ax,cx
	cmp dl,20h				; check if the character is space (array is over)
	je num
	cmp dl,2bh				; check if the character is plus (array is over)
	je summation
	cmp dl,2ah				; check if the character is asterisk (array is over)
	je multiplication
	cmp dl,2fh				; check if the character is slash (array is over)
	je division
	cmp dl,7ch				; check if the character is vertical bar (array is over)
	je bitor
	cmp dl,26h				; check if the character is ampersand (array is over)
	je bitand
	cmp dl,5eh				; check if the character is caret (array is over)
	je bitxor
	cmp dl,0dh				; check if the character is enter (array is over)
	je run				
	cmp dl,39h				; check if the character is A, B, C, D, E or F else go convertdec
	jg convertlet	

convertdec:
	sub dx,'0'				; convert from ascii to real numeric value
	mov temp,dx				; 3 lines later, when we multiply ax and cx, dx changes. so, copy dx's value to temp
	mov ax,cx		
	mov cx,10h
	mul cx					; multiply ax by 10 (shift the number read so far) 320 + 5  
	add ax,temp				; add numeric value of new input character to the number read so far
	mov cx,ax				; put result to cx again
	jmp morechar


convertlet:
	sub dx,'7'				; convert from ascii to real numeric value
	mov temp,dx				; 3 lines later, when we multiply ax and cx, dx changes. so, copy dx's value to temp
	mov ax,cx		
	mov cx,10h
	mul cx					; multiply ax by 10 (shift the number read so far) 320 + 5  
	add ax,temp				; add numeric value of new input character to the number read so far
	mov cx,ax				; put result to cx again
	jmp morechar

num:
	mov n1,cx				; put first number to n1 
	push cx
	mov cx,0				; we will release cx to read the next number
	jmp morechar

summation:
	pop ax					; pop, add and push the result
	pop bx
	add ax,bx
	push ax
	jmp deleting

run:
	cmp ax, cx				; if ax equals cx this means there is only one number in postfix.
	je pushing				; if the postfix consist of only one number, we need to push it stack before setup. 
	jmp setup_string

pushing:
	push ax					; push the only number
	jmp setup_string

multiplication:					
	pop ax					; pop, multiply and push the result
	pop bx
	mul bx
	push ax
	jmp deleting
division:					; pop, divide and push the result
	pop bx
	pop ax
	mov dx, 0h
	div bx
	push ax
	jmp deleting


	
bitor:                                    	; pop, make the or operation and push the result            
	pop ax
	pop bx
	or ax,bx
	push ax
	jmp deleting

bitand:                                         ; pop, make the and operation and push the result           
	pop ax
	pop bx
	and ax,bx
	push ax
	jmp deleting

bitxor:     					; pop, make the xor operation and push the result 
	pop ax
	pop bx
	xor ax,bx
	push ax
	jmp deleting

deleting:
	mov ah,01h				; reads character after operation, according to syntax character can be space or enter
	int 21h					
	mov dx,0				; dx becomes 0 
	mov dl,al
	cmp dl,0dh				; check if the character is enter (array is over)
	je run	
	jmp morechar

setup_string:
	pop ax
	mov bx,offset number+4 			; put a $ at end of buffer
	mov b[bx],"$"				; we will fill buffer from back
	dec bx
	mov cx, 04h

convert_hexadecimal:
	mov dx,0
	mov n1,10h
	div n1	                                      
	cmp dl, 09h				; if the remaninder is A, B, C, D, E or F go to conv2, else go conv1
	jg conv2

conv1:
	add dx,30h  				; convert remainder (last digit) to its ASCII representation
	mov [bx],dl				; and move to buffer for output
	dec bx
	dec cx
	cmp cx,00				; check if we have got all digits
	jnz convert_hexadecimal	
	jmp print_cr

conv2:
	add dx,37h  				; convert remainder (last digit) to its ASCII representation
	mov [bx],dl				; and move to buffer for output
	dec bx
	dec cx
	cmp cx,00				; check if we have got all digits
	jnz convert_hexadecimal	

print_cr:
	mov dl,10
	mov ah,02				; print out a line feed character
	int 21h
	mov dl,13
	mov ah,02				; print out a carriage return character
	int 21h


printout:
	mov dx,bx				; give the address of string to dx
	inc dx					; we decremented once too many, go forward one
	mov ah,09
	int 21h

exit:
	mov ah,04ch				; exit the program with 0
	mov al,00
	int 21h