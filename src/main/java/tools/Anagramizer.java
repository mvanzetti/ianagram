package tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ListIterator;

public class Anagramizer {

    //	input members
    private String anagramizeString;
    private int anagramizeLength;
    private double anagramizeLimit;

    public String getAnagramizeString() {

        return anagramizeString;
    }

    public int getAnagramizeLength() {

        return anagramizeLength;
    }

    public double getAnagramizeLimit() {

        return anagramizeLimit;
    }

    public void addAnagramizeLimit(int AnagramizeLimit) {

        anagramizeLimit += AnagramizeLimit;

    }


    //	manipulation members
    private ArrayList<String> anagramizeList;


    //	state members
    private AnagramizeState anagramizeState;

    private enum tagState {
        isNone,
        isInitialized,
        isComputing,
        isExecuted,
    }

    private class AnagramizeState {
        tagState state;

        public AnagramizeState(tagState State) {

            this.state = State;
        }

        public void setAnagramizeState(tagState State) {

            this.state = State;
        }

        public String getAnagramizeState() {

            String stateMessage;

            switch (state) {

                default:
                case isNone:
                    stateMessage = "none";
                    break;

                case isInitialized:
                    stateMessage = "initialized";
                    break;

                case isComputing:
                    stateMessage = "computing";
                    break;

                case isExecuted:
                    stateMessage = "executed";
                    break;
            }

            return stateMessage;
        }

    }

    public String getState() {

        return anagramizeState.getAnagramizeState();
    }

    //	anagram counter
    private int anagramCounter;

    public int getAnagramCounter() {

        return anagramCounter;
    }

    //	time counter
    private AnagramizeTimeCounter anagramizeTimeCounterMS;

    private class AnagramizeTimeCounter {

        private long timeOffset, timeElapsed;

        //	constructor
        public AnagramizeTimeCounter() {

        }

        public void Start() {

            Calendar now = Calendar.getInstance();
            timeOffset = now.getTimeInMillis();
        }

        public void Stop() {

            Calendar now = Calendar.getInstance();
            timeElapsed = now.getTimeInMillis() - timeOffset;
        }

    }

    public long getTimeCounterMS() {

        return anagramizeTimeCounterMS.timeElapsed;
    }

    public void startTimeCounterMS() {

        anagramizeTimeCounterMS.Start();
    }

    public void stopTimeCounterMS() {

        anagramizeTimeCounterMS.Stop();
    }


    //	info members
    private double distinctPermutationsNumber;


    //	n! is the number of permutations of n elements
    private int CountAllPermutations(int numberOfElements) {

        // base case: n! = 1 when n <= 1
        if (numberOfElements <= 1) {

            return 1;
        }

        // recursive Case
        else {

            return numberOfElements * CountAllPermutations(numberOfElements - 1);
        }

    }

    // Gergo Nemes's Gamma Function implementation
    private static double Gamma(double z) {

        double tmp1 = Math.sqrt(2 * Math.PI / z);
        double tmp2 = z + 1.0 / (12 * z - 1.0 / (10 * z));

        tmp2 = Math.pow(tmp2 / Math.E, z);

        return tmp1 * tmp2;
    }

    private double CountAllPermutationsWithGamma(double numberOfElements) {

        return Gamma((double) numberOfElements + 1.);

    }

    private int CountAllPermutationsIteratively(int numberOfElements) {

        int prod = 1;

        for (int k = 1; k <= numberOfElements; k++) {
            prod *= k;
        }

        return prod;

    }

    //	n!/k! is the number of distinct permutations of n elements when an element occurs k times
    private double CountDistinctPermutations(String permuteString) {

        double permutationsNumber = CountAllPermutationsWithGamma((double) permuteString.length());
        double distinctPermutationsNumber = permutationsNumber;
        double normalizationFactor = 1;

        ArrayList<String> permuteList = new ArrayList<String>();

        for (int i = 0; i < permuteString.length(); i++) {

            permuteList.add(i, permuteString.substring(i, i + 1));
        }

        Collections.sort(permuteList);

        if (permuteList.size() > 1) {

            ListIterator<String> listItr = permuteList.listIterator(0);
            ListIterator<String> nextListItr = permuteList.listIterator(1);

            while (listItr.hasNext()) {

                double frequency = 0;
                Object element = listItr.next();

                if (nextListItr.hasNext() && element.equals(nextListItr.next())) {
                    continue;
                }

                frequency = (double) Collections.frequency(permuteList, element);
                frequency = CountAllPermutationsWithGamma(frequency);
                normalizationFactor *= frequency;

            }
        }

        distinctPermutationsNumber /= normalizationFactor;

        return distinctPermutationsNumber;

    }

    public double getDistinctPermutationsNumber() {
        return distinctPermutationsNumber;
    }


    public Anagramizer(String AnagramizeString) {

        anagramizeString = AnagramizeString;
        anagramizeLength = AnagramizeString.length();

        anagramizeList = new ArrayList<String>();

        for (int i = 0; i < anagramizeString.length(); i++) {

            anagramizeList.add(i, anagramizeString.substring(i, i + 1));
        }

        //	ascending order: compute all the permutations
        Collections.sort(anagramizeList);

        distinctPermutationsNumber = CountDistinctPermutations(anagramizeString);
        anagramizeLimit = distinctPermutationsNumber;

        //	the identity anagram
        anagramOutputString = StringRender(anagramizeList);
        anagramCounter = 1;

        //	set state
        anagramizeState = new AnagramizeState(tagState.isInitialized);

        //	time counter
        anagramizeTimeCounterMS = new AnagramizeTimeCounter();

    }

//    public Anagramizer(String AnagramizeString, int AnagramizeLimit) {
//
//        anagramizeString = AnagramizeString;
//        anagramizeLength = AnagramizeString.length();
//        anagramizeLimit = AnagramizeLimit;
//
//        anagramizeList = new ArrayList<String>();
//
//        for (int i = 0; i < anagramizeString.length(); i++) {
//
//            anagramizeList.add(i, anagramizeString.substring(i, i + 1));
//        }
//
//        Collections.sort(anagramizeList);
//
//        distinctPermutationsNumber = CountDistinctPermutations(anagramizeString);
//
//        //	the identity anagram
//        anagramOutputString = StringRender(anagramizeList);
//        anagramCounter = 1;
//
//        //	set state
//        anagramizeState = new AnagramizeState(tagState.isInitialized);
//
//        //	time counter
//        anagramizeTimeCounterMS = new AnagramizeTimeCounter();
//
//    }
//
//
//    public Anagramizer(String AnagramizeString, int AnagramizeLimit, boolean sort) {
//
//        anagramizeString = AnagramizeString;
//        anagramizeLength = AnagramizeString.length();
//        anagramizeLimit = AnagramizeLimit;
//
//        anagramizeList = new ArrayList<String>();
//
//        for (int i = 0; i < anagramizeString.length(); i++) {
//
//            anagramizeList.add(i, anagramizeString.substring(i, i + 1));
//        }
//
//        if (sort) {
//
//            Collections.sort(anagramizeList);
//        }
//
//        distinctPermutationsNumber = CountDistinctPermutations(anagramizeString);
//
//        //	the identity anagram
//        anagramOutputString = StringRender(anagramizeList);
//        anagramCounter = 1;
//
//        //	set state
//        anagramizeState = new AnagramizeState(tagState.isInitialized);
//
//        //	time counter
//        anagramizeTimeCounterMS = new AnagramizeTimeCounter();
//
//
//    }


    //	output members
    private String anagramOutputString;

    public String getAnagram() {

        return anagramOutputString;
    }

    public boolean NextAnagram() {

        if (anagramCounter == anagramizeLimit)
            return false;

        anagramizeState.setAnagramizeState(tagState.isComputing);

        boolean returnValue = NextPermutation(anagramizeList);

        anagramOutputString = StringRender(anagramizeList);
        anagramCounter++;

        anagramizeState.setAnagramizeState(tagState.isExecuted);

        return returnValue;
    }


    //	render to string
    private String StringRender(ArrayList<String> permuteList) {

        StringBuilder sb = new StringBuilder();

        for (String el : permuteList) {

            sb.append(el);
        }

        return sb.toString();
    }

    //	reversing of a portion of a string collection
    private void ReverseInterval(ArrayList<String> permuteList, int startIndex, int endIndex) {

        ArrayList<String> leftSequence = new ArrayList<String>();
        ArrayList<String> centerSequence = new ArrayList<String>();
        ArrayList<String> rightSequence = new ArrayList<String>();

        for (int i = 0; i < startIndex; i++) {

            leftSequence.add(permuteList.get(i));
        }

        for (int i = startIndex; i < endIndex; i++) {

            centerSequence.add(permuteList.get(i));
        }

        for (int i = endIndex; i < permuteList.size() - 1; i++) {

            rightSequence.add(permuteList.get(i));
        }

        Collections.reverse(centerSequence);

        permuteList.clear();

        permuteList.addAll(leftSequence);
        permuteList.addAll(startIndex, centerSequence);
        permuteList.addAll(endIndex, rightSequence);

    }

    //	the beautiful next permutation implementation
    private boolean NextPermutation(ArrayList<String> permuteList) {

        ListIterator<String> first = permuteList.listIterator(0);
        ListIterator<String> last = permuteList.listIterator(permuteList.size());

        // check: sequences of length 0 or 1 have only 1 permutation (themselves)
        if (!first.hasNext())
            return false;

        first.next();
        if (!first.hasNext())
            return false;

        first.previous();    // roll back: check passed

        // begin the search loop, starting from the end of the sequence
        ListIterator<String> i = permuteList.listIterator(permuteList.size() - 1);

        // search from the end to the beginning of the sequence
        for (; ; ) {

            // i.previous() will return the [n] element, ii.next() the [n+1] element
            ListIterator<String> ii = permuteList.listIterator(i.nextIndex());

            String iElement = i.previous();
            String iiElement = ii.next();

            // lexicographically compare: searching for the first two successive elements (i, ii)
            // where the value of the n+1th (ii) is greater than the value of the nth (i)
            if (iElement.compareTo(iiElement) < 0) {

                ListIterator<String> j = last;
                //	String jElement = j.previous();

                // lexicographically compare: searching for the first element from the end
                // that is greater than the element pointed by iElement
                while (!(iElement.compareTo(j.previous()) < 0)) ;

                // swap iElement and jElement
                Collections.swap(permuteList, i.nextIndex(), j.nextIndex());

                int startIndex = ii.previousIndex();
                int endIndex = permuteList.size();

                // now reverse from iiElement to last
                ReverseInterval(permuteList, startIndex, endIndex);

                return true;
            }

            // i points to the beginning of the list: no more permutations
            if (!i.hasPrevious()) {

                // now reverse from first to last
                Collections.reverse(permuteList);

                return false;
            }


        }

    }
}
