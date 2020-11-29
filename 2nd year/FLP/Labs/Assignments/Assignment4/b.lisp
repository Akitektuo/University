#||
deepProduct(l1..ln) =
	1, n = 0
	l1 * deepProduct(l2..ln), l1 is number
	deepProduct(l1) * deepProduct(l2..ln), l1 is list
	deepProduct(l2..ln), otherwise
	
deepProduct(lisp: List): Integer
||#
(defun deep-product (list-param)
	(cond
		((null list-param) 1)
		((numberp (car list-param))
			(* (car list-param)
				(deep-product (cdr list-param))))
		((listp (car list-param))             
			(* (deep-product (car list-param))
				(deep-product (cdr list-param))))
		((deep-product (cdr list-param)))))

#||
product(l1..ln) =
	0, n = 0
	deepProduct(l1..ln), otherwise

product(list: List): Integer
||#
(defun product (list-param)
	(if list-param
		(deep-product list-param)
		0))

(defvar input-list
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))

(terpri)
(write (product input-list))