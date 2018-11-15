package ru.otus.h8.facevalue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectionModel {
    private List<BundleOfBills> content = new ArrayList<>();

    public SelectionModel() {}

    public void putFaceValues(Nominal nominal, int billsCount) {
        if (hasNominal(nominal)) {
            getNominal(nominal).putFaceValues(billsCount);
        } else {
            content.add(new BundleOfBills(nominal, billsCount));
            content.sort((o1, o2) -> o2.getNominal().asNominal() - o1.getNominal().asNominal());
        }
    }

    public void putFaceValue(Nominal nominal) {
        putFaceValues(nominal, 1);
    }

    public boolean getFaceValue(Nominal nominal) {
        if (isEmpty() || hasNominal(nominal)) {
            return false;
        }
        return getNominal(nominal).getFaceValue();
    }

    public int getFaceValues(Nominal nominal, int numFaceValues) {
        if (isEmpty() || !hasNominal(nominal)) {
            return 0;
        }
        BundleOfBills bb = getNominal(nominal);
        int result = bb.getFaceValues(numFaceValues);
        if (bb.isEmpty()) {
            content.remove(bb);
        }
        return result;
    }

    public int getFaceValues(Nominal nominal) {
        if (!isEmpty() && hasNominal(nominal)) {
            return 0;
        }
        return getFaceValues(nominal, getNominal(nominal).getBillsCount());
    }

    public int getBillsCount(Nominal nominal) {
        BundleOfBills bb = getNominal(nominal);
        if (bb != null) {
            return bb.getBillsCount();
        } else {
            return 0;
        }
    }

    public boolean hasNominal(Nominal nominal) {
        for (BundleOfBills bb : content) {
            if (bb.getNominal() == nominal) {
                return true;
            }
        }
        return false;
    }

    private BundleOfBills getNominal(Nominal nominal) {
        for (BundleOfBills bb : content) {
            if (bb.getNominal() == nominal) {
                return bb;
            }
        }
        return null;
    }

    public List<BundleOfBills> getContent() {
        return content;
    }

    public boolean isEmpty() {
        return content.isEmpty() || getSum() == 0;
    }

    public int getSum() {
        int total = 0;
        for (BundleOfBills bb : content) {
            total += bb.getSumm();
        }
        return total;
    }

    @Override
    public String toString() {
        return content.stream()
                .map(BundleOfBills::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    protected SelectionModel clone() {
        SelectionModel bs = new SelectionModel();
        bs.content = copyContent();
        return bs;
    }

    private List<BundleOfBills> copyContent() {
        List<BundleOfBills> copy = new ArrayList<>();
        for (BundleOfBills bb : content) {
            copy.add(bb.clone());
        }
        return copy;
    }
}

