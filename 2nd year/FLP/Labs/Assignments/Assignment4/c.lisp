; Write a function to sort a linear list with keeping the double values.

#||
insert(l1..ln, element) =
	[element], n = 0
	element (+) l1..ln, element = l1 or element < l1
	l1 (+) insert(l2..ln), otherwise

insert(list: List, element: Integer): List
||#
(defun insert (list-param element)
	(cond
		((null list-param) (list element))
		((or
			(equal (car list-param) element)
			(< element (car list-param))) (cons element list-param))
		((cons
			(car list-param)
			(insert (cdr list-param) element)))))

#||
sortList(l1..ln) =
	[], n = 0
	insert(sortList(l2..ln), l1), otherwise

sortList(list: List): List
||#
(defun sort-list (list-param)
	(if list-param
		(insert (sort-list (cdr list-param)) (car list-param))
		'()))

(defvar input-list
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))

(terpri)
(write (sort-list input-list))