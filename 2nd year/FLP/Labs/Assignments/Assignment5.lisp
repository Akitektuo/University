;  For a given tree of type (1 - (A 2 B 0 C 2 D 0 E 0)) return the path from the root node to a certain given node X.

#||
getPathToTarget(l1..ln, r1..rm) = {
	r1..rm, n = 0
	l1..ln, otherwise
}
getPathToTarget(leftPath: list, rightPath: list): list
||#
(defun get-path-to-target (left-path right-path)
	(if left-path left-path right-path))

#||
addPath(currentNode, p1..pn, r1..rm) = {
	[p1..pn, r1..rm], n = 0
	[currentNode (+) p1..pn, r1..rm], otherwise
}
addPath(currentNode: atom, paths: list, rest: list): list
(Returns pair of 'paths' and 'rest of tree to be evaluated' as a list)
||#
(defun add-path (current-node paths rest)
	(if paths
		(list (cons current-node paths) rest)
		(list paths rest)))

#||
computePath(t1..tn, targetNode) = {
	[[], []], n = 0
	[[targetNode], []], t1 = targetNode
	[[], t3..tn], t2 = 0
	s1..sm := computePath(t3..tn, targetNode), addPath(t1, s1, s2), t2 = 1
	l1..lm := computePath(t3..tn, targetNode), r1..rk := computePath(l2, targetNode), addPath(t1, getPathToTarget(l1, r1), []), otherwise
}
computePath(binaryTree: list, targetNode: atom): list
(Returns pair of 'found path' and 'rest of tree to be evaluated' as a list)
||#
(defun compute-path (binary-tree target-node)
	(cond
		((null binary-tree)
			(list '() '()))
		((equal (car binary-tree) target-node)
			(list (list target-node) '()))
		((equal (cadr binary-tree) 0)
			(list '() (cddr binary-tree)))
		((equal (cadr binary-tree) 1)
			(let ((result (compute-path (cddr binary-tree) target-node)))
				(add-path (car binary-tree) (car result) (cadr result))))
		((equal (cadr binary-tree) 2)
			(let ((left-result (compute-path (cddr binary-tree) target-node)))
				(let ((right-result (compute-path (cadr left-result) target-node)))
					(add-path (car binary-tree) (get-path-to-target (car left-result) (car right-result)) '()))))))
			
#||
getPath(t1..tn, targetNode) = {
	[], n = 0
	r1..rm := computePath(t1..tn, targetNode), r1, otherwise
}
getPath(binaryTree: list, targetNode: atom): list
||#			
(defun get-path (binary-tree target-node)
	(if binary-tree
		(car (compute-path binary-tree target-node))
		'()))

(defvar input-list
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))
(defvar input-node (read))

(terpri)
(write (get-path input-list input-node))