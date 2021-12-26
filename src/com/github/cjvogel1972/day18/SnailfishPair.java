package com.github.cjvogel1972.day18;

public class SnailfishPair implements SnailfishElement {
    private SnailfishElement left;
    private SnailfishElement right;
    private SnailfishPair parent;

    public SnailfishPair(SnailfishElement left, SnailfishElement right) {
        this.left = left;
        if (left instanceof SnailfishPair pair) {
            pair.parent = this;
        }
        this.right = right;
        if (right instanceof SnailfishPair pair) {
            pair.parent = this;
        }
    }

    public SnailfishPair(SnailfishPair left, SnailfishPair right) {
        this.left = left;
        left.parent = this;
        this.right = right;
        right.parent = this;
    }

    public SnailfishPair(int left, int right) {
        this.left = new SnailfishNumber(left);
        this.right = new SnailfishNumber(right);
    }

    public SnailfishPair(int left, SnailfishPair right) {
        this.left = new SnailfishNumber(left);
        this.right = right;
        right.parent = this;
    }

    public SnailfishPair(SnailfishPair left, int right) {
        this.left = left;
        left.parent = this;
        this.right = new SnailfishNumber(right);
    }

    @Override
    public SnailfishElement add(SnailfishElement term) {
        if (term instanceof SnailfishPair pair) {
            var result = new SnailfishPair(copy(), pair.copy());
//            this.parent = result;

            return result;
        }
        return null;
    }

    @Override
    public boolean reduce() {
        boolean reduced = true;
        while (reduced) {
            reduced = doReduction(false);

            if (!reduced) {
                reduced = doReduction(true);
            }
        }

        return true;
    }

    boolean doReduction(boolean split) {
        //        System.out.println(this);
        var reduced = false;

        var parentCount = findParentCount();
        if (parentCount == 4) {
//            System.out.println("explode = " + this);
            explode();
            reduced = true;
        }

        if (!reduced && left instanceof SnailfishPair pair) {
            reduced = pair.doReduction(split);
        } else if (!reduced && split && ((SnailfishNumber) left).getNumber() > 9) {
//            System.out.println("split " + this);
            var newPair = split(((SnailfishNumber) left).getNumber());
            left = newPair;
            reduced = true;
        }
        if (!reduced && right instanceof SnailfishPair pair) {
            reduced = pair.doReduction(split);
        } else if (!reduced && split && ((SnailfishNumber) right).getNumber() > 9) {
//            System.out.println("split " + this);
            var newPair = split(((SnailfishNumber) right).getNumber());
            right = newPair;
            reduced = true;
        }

        return reduced;
    }

    @Override
    public int magnitude() {
        return ((3 * left.magnitude()) + (2 * right.magnitude()));
    }

    @Override
    public SnailfishElement copy() {
        var newLeft = left.copy();
        var newRight = right.copy();
        return new SnailfishPair(newLeft, newRight);
    }

    private void explode() {
        var c = this;
        var p = parent;
        var foundLeft = false;

        var addLeft = true;
        while (p != null && !foundLeft) {
            if (p.left == c) {
                c = p;
                p = p.parent;
            } else {
                if (p.left instanceof SnailfishPair) {
                    addLeft = false;
                    var l = p.left;
                    do {
                        if (l instanceof SnailfishNumber) {
                            if (addLeft) {
                                l.add(left);
                            } else {
//                                System.out.println("l.add(left)");
                                l.add(left);
                            }
                            foundLeft = true;
                        } else {
                            l = ((SnailfishPair) l).right;
                        }
                    } while (!foundLeft);
                } else {
                    if (addLeft) {
                        p.left.add(left);
                    } else {
                        p.right.add(left);
                    }
                    foundLeft = true;
                }
            }
        }

        c = this;
        p = parent;
        var foundRight = false;
        var addRight = true;

        while (p != null && !foundRight) {
            if (p.right == c) {
                c = p;
                p = p.parent;
            } else {
                if (p.right instanceof SnailfishPair) {
                    addRight = false;
                    var r = p.right;
                    do {
                        if (r instanceof SnailfishNumber) {
                            if (addRight) {
                                r.add(right);
                            } else {
//                                System.out.println("r.add(right)");
                                r.add(right);
                            }
                            foundRight = true;
                        } else {
                            r = ((SnailfishPair) r).left;
                        }
                    } while (!foundRight);
                } else {
                    if (addRight) {
                        p.right.add(right);
                    } else {
                        p.left.add(right);
                    }
                    foundRight = true;
                }
            }
        }

        if (parent.left == this) {
            parent.left = new SnailfishNumber(0);
        } else {
            parent.right = new SnailfishNumber(0);
        }
    }

    private boolean split() {
        var result = false;

        if (left instanceof SnailfishNumber num) {
            if (num.getNumber() > 9) {
                var number = num.getNumber();
                var newLeft = new Double(Math.floor(number / 2.0)).intValue();
                var newRight = new Double(Math.ceil(number / 2.0)).intValue();
                var newPair = new SnailfishPair(newLeft, newRight);
                newPair.parent = this;
                left = newPair;
                result = true;
            }
        }
        if (right instanceof SnailfishNumber num && !result) {
            if (num.getNumber() > 9) {
                var number = num.getNumber();
                var newLeft = new Double(Math.floor(number / 2.0)).intValue();
                var newRight = new Double(Math.ceil(number / 2.0)).intValue();
                var newPair = new SnailfishPair(newLeft, newRight);
                newPair.parent = this;
                right = newPair;
                result = true;
            }
        }

        return result;
    }

    private SnailfishPair split(int number) {
        var newLeft = new Double(Math.floor(number / 2.0)).intValue();
        var newRight = new Double(Math.ceil(number / 2.0)).intValue();
        var newPair = new SnailfishPair(newLeft, newRight);
        newPair.parent = this;
        return newPair;
    }

    private int findParentCount() {
        var count = 0;

        var p = parent;
        while (p != null) {
            count++;
            p = p.parent;
        }

        return count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(left);
        sb.append(",");
        sb.append(right);
        sb.append("]");

        return sb.toString();
    }
}
