#||
isElementInList(element, l1..ln) =
	false, n = 0
	true, element = l1
	isElementInList(element, l2..ln), otherwise
	
isElementInList(element: Integer, inList: List): Boolean
||#
(defun is-element-in-list (element in-list)
	(cond
		((null in-list) nil)
		((eq element (car in-list)) T)
		((is-element-in-list element (cdr in-list)))))

#||
recusrsiveUnion(l1..ln, k1..km) =
	k1..km, n = 0
	recusrsiveUnion(l2..ln, k1..km), isElementInList(l1, k1..km)
	recusrsiveUnion(l2..ln, l1 (+) k1..km)

recusrsiveUnion(listA: List, listB: List): List
||#
(defun recursive-union (list1 list2)
	(cond
		((null list1) list2)
		((is-element-in-list (car list1) list2)
			(recursive-union (cdr list1) list2))
		((recursive-union
			(cdr list1)
			(cons (car list1) list2)))))

(defvar list1
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))
		
(defvar list2
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))

(terpri)
(write (recursive-union list1 list2))
