; Define a function to tests the membership of a node in a n-tree represented as (root
; list_of_nodes_subtree1 ... list_of_nodes_subtreen)
; Eg. tree is (a (b (c)) (d) (E (f))) and the node is "b" => true
#||
testMembership(l, node) = {
	false, n = 0
	true, l = node
	false, l is atom and l != node
	or (testMembership(li, node), i=1..n) where l is l1..ln
}
testMembership(tree: list/atom, node: atom): boolean
||#
(defun test-membership (tree target-node)
	(cond
		((null tree) nil)
		((equal tree target-node) t)
		((atom tree) nil)
		((eval 
			(cons 'or (mapcar
				(lambda
					(sub-tree)
					(test-membership sub-tree target-node))
				tree))))))

; a (b (c)) (d) (e (f))
; b

(defvar input-list
	(read-from-string
		(concatenate 'string "(" (read-line) ")")))
(defvar input-node (read))

(terpri)
(write (test-membership input-list input-node))