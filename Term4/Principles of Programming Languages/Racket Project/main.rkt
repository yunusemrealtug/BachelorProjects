#lang racket

(provide (all-defined-out))

; 10 points
(define := (lambda (var value) (list var value)))
; 10 points
(define -- (lambda args (list 'let args)))
; 10 points
(define @ (lambda (bindings expr) (append bindings expr)))
; 20 points
(define split_at_delim (lambda (delim args)
  (foldr (lambda (element next)
           (if (equal? element delim)
               (cons '() next)
               (cons (cons element (car next)) (cdr next))))
         (list '()) args)))

(define out_par (lambda (args)
    (if (equal? (length args) 1)
        (if (list? (first args))
            (out_par (first args))
            (first args))
        (parse_expr args))))

(define in-list?
  (lambda (s lst)
    (cond
      [(empty? lst) #f]
      [(eq? s (first lst)) #t]
      [else (in-list? s (rest lst))])))
            
(define take_par (lambda (args)
    (if (equal? (length args) 1) (list (out_par (first args))) (cons (out_par (first args)) (take_par (rest args))))))
(define binding (lambda (args)
    (if (equal? (length args) 4) (list(eval(cons ':= (remove (fourth args) (remove (second args) args))))) (append (list(eval(cons ':= (list (first args) (third args))))) (binding (rest(rest(rest (rest args)))))))))
; 30 points
(define parse_expr (lambda (expr) 
     (cond
   [(in-list? '+ expr) (take_par (append '((+)) (split_at_delim '+ expr)))]
   [(in-list? '* expr) (take_par (append '((*)) (split_at_delim '* expr)))]
   [(in-list? '@ expr) (@ (cons 'let (list(binding (first expr)))) (list (parse_expr (third expr))))]
   [else (
          if (list? (first expr))
             (parse_expr (first expr))
             (first expr)
          )])))
; 20 points
(define eval_expr (lambda (expr) (eval (parse_expr expr))))


