package com.github.cjvogel1972.day18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnailfishPairTest {

    @Test
    void add() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[4,3],4],4],[7,[[8,4],9]]]");
        SnailfishPair pair2 = new SnailfishPair(1, 1);

        SnailfishElement result = pair.add(pair2);

        assertEquals("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", result.toString());
    }

    @Test
    void reduceExplodeNoLeftNumber() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[[9,8],1],2],3],4]");
        pair.reduce();

        assertEquals("[[[[0,9],2],3],4]", pair.toString());
    }

    @Test
    void reduceExplodeNoRightNumber() {
        SnailfishPair pair = SnailfishParser.parseLine("[7,[6,[5,[4,[3,2]]]]]");
        pair.reduce();

        assertEquals("[7,[6,[5,[7,0]]]]", pair.toString());
    }

    @Test
    void reduceExplodeBothLeftRightNumber() {
        SnailfishPair pair = SnailfishParser.parseLine("[[6,[5,[4,[3,2]]]],1]");
        pair.reduce();

        assertEquals("[[6,[5,[7,0]]],3]", pair.toString());
    }

    @Test
    void reduceTwoExplodesOnlyDoOne() {
        SnailfishPair pair = SnailfishParser.parseLine("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        pair.doReduction(false);

        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", pair.toString());
    }

    @Test
    void reduceExplodesFourDownRight() {
        SnailfishPair pair = SnailfishParser.parseLine("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        pair.reduce();

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", pair.toString());
    }

    @Test
    void reduceSplit() {
        SnailfishPair pair1 = new SnailfishPair(0, 7);
        SnailfishPair pair2 = new SnailfishPair(pair1, 4);

        SnailfishPair pair3 = new SnailfishPair(0, 13);
        SnailfishPair pair4 = new SnailfishPair(15, pair3);

        SnailfishPair pair5 = new SnailfishPair(pair2, pair4);

        SnailfishPair pair6 = new SnailfishPair(1, 1);

        SnailfishPair pair7 = new SnailfishPair(pair5, pair6);

        assertEquals("[[[[0,7],4],[15,[0,13]]],[1,1]]", pair7.toString());

        pair7.doReduction(true);

        assertEquals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", pair7.toString());
    }

    @Test
    void fullReduce() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        pair.reduce();

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", pair.toString());
    }

    @Test
    void fullReduce2() {
        SnailfishPair pair1 = SnailfishParser.parseLine("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]");
        SnailfishPair pair2 = SnailfishParser.parseLine("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]");
        SnailfishPair pair = (SnailfishPair) pair1.add(pair2);
        pair.reduce();

        assertEquals("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", pair.toString());
    }

    @Test
    void fullReduce3() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]],[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]]");
        pair.reduce();

        assertEquals("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]", pair.toString());
    }

    @Test
    void magnitude1() {
        SnailfishPair pair = SnailfishParser.parseLine("[[1,2],[[3,4],5]]");

        assertEquals(143, pair.magnitude());
    }

    @Test
    void magnitude2() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");

        assertEquals(1384, pair.magnitude());
    }

    @Test
    void magnitude3() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[1,1],[2,2]],[3,3]],[4,4]]");

        assertEquals(445, pair.magnitude());
    }

    @Test
    void magnitude4() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[3,0],[5,3]],[4,4]],[5,5]]");

        assertEquals(791, pair.magnitude());
    }

    @Test
    void magnitude5() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[5,0],[7,4]],[5,5]],[6,6]]");

        assertEquals(1137, pair.magnitude());
    }

    @Test
    void magnitude6() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");

        assertEquals(3488, pair.magnitude());
    }

    @Test
    void magnitude7() {
        SnailfishPair pair = SnailfishParser.parseLine("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]");

        assertEquals(4140, pair.magnitude());
    }
}