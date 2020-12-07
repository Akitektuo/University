; Build a list which contains positions of a minimum numeric element from a given linear list.

#||
minPositions(l1..ln, minNum, index, k1..km) =
	k1..km, n = 0
	minPositions(l2..ln, l1, index + 1, [index]), l1 is number and l1 < minNum
	minPositions(l2..ln, minNum, index + 1, index (+) k1..km), l1 is number and l1 = minNum
	minPositions(l2..ln, minNum, index + 1, k1..km), otherwise

minPositions(list: List, minimumNumber: Integer, index: Integer, minimumNumberIndexes: List): List
||#
(defun min-positions (list-param min-num index indexes)
	(cond
		((null list-param) indexes)
		((and
			(numberp (car list-param))
			(< (car list-param) min-num))
			(min-positions (cdr list-param) (car list-param) (+ index 1) (list index)))
		((and
			(numberp (car list-param))
			(= (car list-param) min-num))
			(min-positions (cdr list-param) min-num (+ index 1) (cons index indexes)))
		((min-positions (cdr list-param) min-num (+ index 1) indexes))))

#||
getMinPositions(l1..ln) =
	[], n = 0
	minPositions(l1..ln, 9999, 1, []), otherwise

getMinPositions(list: List): List
||#
(defun get-min-positions (list-param)
	(if list-param
		(min-positions list-param 99999 1 `())
		'()))

(defvar input-list
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))

(terpri)
(write (get-min-positions input-list))