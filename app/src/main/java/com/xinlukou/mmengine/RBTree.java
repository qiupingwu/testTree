package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class RBTree {
	public Entry root;
    public Integer size;
    public Integer compareType;
    private static Byte BLACK = 0;
    private static Byte RED = 1;

    private Entry getMaxEntry() {
        Entry p = root;
        if(p != null) {
            while(p != null) {
                if(p.right != null) p = p.right;
                else break;
            }
        }
        return p;
    }

    private Entry getMinEntry() {
        Entry p = root;
        if(p != null) {
            while(p != null) {
                if(p.left != null) p = p.left;
                else break;
            }
        }
        return p;
    }

    private Entry findEntry(Object key) {
        Entry p = root;
        while(p != null) {
            Integer cmp = compareData(key, p.data);
            if(cmp == 0) return p;
            else if(cmp < 0) p = p.left;
            else p = p.right;
        }
        return null;
    }

    private void rotateLeft(Entry target) {
        Entry r = target.right;
        target.right = r.left;
        if(r.left != null) r.left.parent = target;
        r.parent = target.parent;
        if(target.parent == null) root = r;
        else if(target.parent.left == target) target.parent.left = r;
        else target.parent.right = r;
        r.left = target;
        target.parent = r;
    }

    private void rotateRight(Entry target) {
        Entry l = target.left;
        target.left = l.right;
        if(l.right != null) l.right.parent = target;
        l.parent = target.parent;
        if(target.parent == null) root = l;
        else if(target.parent.right == target) target.parent.right = l;
        else target.parent.left = l;
        l.right = target;
        target.parent = l;
    }

    private void fixAfterInsertion(Entry x) {
        x.color = RED;
        while(x != null && x.parent != null && x.parent.color == RED) {
            if(x.parent == x.parent.parent.left) {
                Entry y = x.parent.parent.right;
                if(y != null && y.color == RED) {
                    x.parent.color = BLACK;
                    y.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if(x == x.parent.right) {
                        x = x.parent;
                        rotateLeft(x);
                    }
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    if(x.parent.parent != null) rotateRight(x.parent.parent);
                }
            } else {
                Entry y = x.parent.parent.left;
                if(y != null && y.color == RED) {
                    x.parent.color = BLACK;
                    y.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if(x == x.parent.left) {
                        x = x.parent;
                        rotateRight(x);
                    }
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    if(x.parent.parent != null) rotateLeft(x.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void fixAfterDeletion(Entry target) {
        while (target != root && target.color == BLACK) {
            if (target == target.parent.left) {
                Entry sib = target.parent.right;
                if(sib != null && sib.color == RED) {
                    sib.color = BLACK;
                    target.parent.color = RED;
                    rotateLeft(target.parent);
                    sib = target.parent.right;
                }
                if((sib.left == null || sib.left.color == BLACK) && (sib.right == null || sib.right.color == BLACK)) {
                    sib.color = RED;
                    target = target.parent;
                } else {
                    if(sib.right == null || sib.right.color == BLACK) {
                        if(sib.left != null) sib.left.color = BLACK;
                        sib.color = RED;
                        rotateRight(sib);
                        sib = target.parent.right;
                    }
                    sib.color = target.parent.color;
                    target.parent.color = BLACK;
                    if(sib.right != null) sib.right.color = BLACK;
                    rotateLeft(target.parent);
                    target = root;
                }
            } else {
                Entry sib = target.parent.left;
                if(sib != null && sib.color == RED) {
                    sib.color = BLACK;
                    target.parent.color = RED;
                    rotateRight(target.parent);
                    sib = target.parent.left;
                }
                if((sib.right == null || sib.right.color == BLACK) && (sib.left == null || sib.left.color == BLACK)) {
                    sib.color = RED;
                    target = target.parent;
                } else {
                    if(sib.left == null || sib.left.color == BLACK) {
                        if(sib.right != null) sib.right.color = BLACK;
                        sib.color = RED;
                        rotateLeft(sib);
                        sib = target.parent.left;
                    }
                    sib.color = target.parent.color;
                    target.parent.color = BLACK;
                    if(sib.left != null) sib.left.color = BLACK;
                    rotateRight(target.parent);
                    target = root;
                }
            }
        }
        target.color = BLACK;
    }

    private void deleteEntry(Entry target) {
        if(target.left != null && target.right != null) {
            Entry s = target.right;
            while(s.left != null) s = s.left;
            target.data = s.data;
            target = s;
        }
        Entry replacement = (target.left != null ? target.left : target.right);
        if(replacement != null) {
            replacement.parent = target.parent;
            if(target.parent == null) root = replacement;
            else if(target == target.parent.left) target.parent.left = replacement;
            else target.parent.right = replacement;
            if(target.color == BLACK) fixAfterDeletion(replacement);
        } else if(target.parent == null) {
            root = null;
        } else {
            if(target.color == BLACK) fixAfterDeletion(target);
            if(target == target.parent.left) target.parent.left = null;
            else target.parent.right = null;
        }
    }

    public Boolean contains(Object data) {
        return findEntry(data) != null;
    }

    public Integer compareData(Object obj1, Object obj2) {
    	if(compareType == 1) return Compare.compareInt(Integer.parseInt(obj1.toString()), Integer.parseInt(obj2.toString()));
    	else if(compareType == 2) return Compare.comparePairInt((PairInt)obj1, (PairInt)obj2);
    	else if(compareType == 6) return Compare.compareRouteTraverseInfo((RouteTraverseInfo)obj1, (RouteTraverseInfo)obj2);
    	else if(compareType == 7) return Compare.compareRouteTraverseDetail((RouteTraverseDetail)obj1, (RouteTraverseDetail)obj2);
    	else if(compareType == 9) return Compare.compareRouteAGCT((RouteAGCT)obj1, (RouteAGCT)obj2);
    	else if(compareType == 12) return Compare.compareRoutePart((RoutePart)obj1, (RoutePart)obj2);
    	else if(compareType == 15) return Compare.compareRouteAGCTData((RouteAGCT)obj1, (RouteAGCT)obj2);
    	else if(compareType == 16) return Compare.compareRouteAGCTPattern((RouteAGCT)obj1, (RouteAGCT)obj2);
    	else return 0;
    }

    public Boolean insertUnique(Object data) {
        if(root == null) {
            root = new Entry(data, null);
            root.color = BLACK;
            size++;
            return true;
        }
        Entry t = root;
        while(true) {
            Integer cmp = compareData(data, t.data);
            if(cmp == 0) {
                return false;
            } else if(cmp < 0) {
                if(t.left != null) {
                    t = t.left;
                } else {
                    t.left = new Entry(data, t);
                    fixAfterInsertion(t.left);
                    size++;
                    return true;
                }
            } else if(cmp > 0) {
                if(t.right != null) {
                    t = t.right;
                } else {
                    t.right = new Entry(data, t);
                    fixAfterInsertion(t.right);
                    size++;
                    return true;
                }
            }
        }
    }

    public Boolean insertUnion(Object data) {
        if(root == null) {
            root = new Entry(data, null);
            root.color = BLACK;
            size++;
            return true;
        }
        Entry t = root;
        while(true) {
            Integer cmp = compareData(data, t.data);
            if(cmp <= 0) {
                if(t.left != null) {
                    t = t.left;
                } else {
                    t.left = new Entry(data, t);
                    fixAfterInsertion(t.left);
                    size++;
                    return true;
                }
            } else {
                if(t.right != null) {
                    t = t.right;
                } else {
                    t.right = new Entry(data, t);
                    fixAfterInsertion(t.right);
                    size++;
                    return true;
                }
            }
        }
    }

    public Object update(Object data, Integer type) {
        // Failure:self Insert:null Update:OldData
        if(root == null) {
            root = new Entry(data, null);
            root.color = BLACK;
            size++;
            return null;
        }
        Entry t = root;
        while(true) {
            Integer cmp = compareData(data, t.data);
            if(cmp == 0) {
                if(compareType == type) {
                    Object oldData = t.data;
                    t.data = data;
                    return oldData;
                } else {
                    Integer backupType = compareType;
                    compareType = type;
                    cmp = compareData(data, t.data);
                    compareType = backupType;
                    if(cmp < 0) {
                        Object oldData = t.data;
                        t.data = data;
                        return oldData;
                    } else {
                        return data;
                    }
                }
            } else if(cmp < 0) {
                if(t.left != null) {
                    t = t.left;
                } else {
                    t.left = new Entry(data, t);
                    fixAfterInsertion(t.left);
                    size++;
                    return null;
                }
            } else if(cmp > 0) {
                if(t.right != null) {
                    t = t.right;
                } else {
                    t.right = new Entry(data, t);
                    fixAfterInsertion(t.right);
                    size++;
                    return null;
                }
            }
        }
    }

    public Object find(Object data) {
        Entry entry = findEntry(data);
        if(entry != null) return entry.data;
        else return null;
    }

    public Object getMax() {
        Entry obj = getMaxEntry();
        if(obj != null) return obj.data;
        else return null;
    }

    public Object getMin() {
        Entry obj = getMinEntry();
        if(obj != null) return obj.data;
        else return null;
    }

    public Object removeMax() {
        Entry max = getMaxEntry();
        if(max == null) return null;
        Object oldValue = max.data;
        deleteEntry(max);
        size--;
        return oldValue;
    }

    public Object removeMin() {
        Entry min = getMinEntry();
        if(min == null) return null;
        Object oldValue = min.data;
        deleteEntry(min);
        size--;
        return oldValue;
    }

    public Object remove(Object key) {
        Entry p = findEntry(key);
        if(p == null) return null;
        Object oldValue = p.data;
        deleteEntry(p);
        size--;
        return oldValue;
    }

    public void traverseInOrder(Entry start, List<Object> inOrderList) {
        if(start != null) {
            traverseInOrder(start.left, inOrderList);
            inOrderList.add(start.data);
            traverseInOrder(start.right, inOrderList);
        }
    }

    public Object getInOrderList() {
        List<Object> inOrderList = new ArrayList<Object>(size);
        traverseInOrder(root, inOrderList);
        return inOrderList;
    }

    public RBTree(Integer type) {
        root = null;
        size = 0;
        compareType = type;
    }

    public void free(Entry obj) {
        if(obj.left == null && obj.right == null) {
            if(obj.parent != null) {
                if(obj.parent.left == obj) obj.parent.left = null;
                else obj.parent.right = null;
            }
            return;
        } else if(obj.left != null && obj.right == null) {
            free(obj.left);
        } else if(obj.left == null && obj.right != null) {
            free(obj.right);
        } else {
            free(obj.left);
            free(obj.right);
        }
        free(obj);
    }

    public void dealloc() {
        if(root != null) {
            free(root);
            root = null;
        }
    }
}
