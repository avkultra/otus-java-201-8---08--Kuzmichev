package ru.otus.h8.atm;

import ru.otus.h8.facevalue.BundleOfBills;
import ru.otus.h8.facevalue.SelectionModel;
import ru.otus.h8.facevalue.Nominal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ATMModel {

    private List<Cell> cells = new ArrayList<>();
    private Memento initMemento;

    private ExcludeStrategy excludeStrategy;
    private final String name;
    private static int iatm = 0;

    public ATMModel(ExcludeStrategy excludeStrategy) {
        this.excludeStrategy = excludeStrategy;
        this.name = "ATM_" + iatm++;
        //saveState();
    }

    public ATMModel(ExcludeStrategy excludeStrategy, String name) {
        this.excludeStrategy = excludeStrategy;
        this.name = name;
        //saveState();
    }

    public void load(SelectionModel sm) {
        List<BundleOfBills> banknotes = sm.getContent();
        for (BundleOfBills bundle : banknotes) {
            Cell cell = getCell(bundle.getNominal());
            if (null == cell) {
                cell = new Cell(bundle.getNominal());
                cells.add(cell);
            }
            int loaded = cell.loadFaceValues(bundle.getBillsCount());
            bundle.getFaceValues(loaded);
        }
    }

    public SelectionModel empty() {
        if (isEmpty()) return null;
        SelectionModel sm = new SelectionModel();
        for (Cell cell : cells) {
            sm.putFaceValues(cell.getNominal(), cell.getBillsCount());
        }
        cells.clear();
        return sm;
    }

    public SelectionModel exclude(int sumExclude) throws ATMExcludeException {
        if (cells.isEmpty() ||  null == excludeStrategy) throw new RuntimeException("Системная ошибка");
        List<Cell> tmpCells = copyCells(cells);
        SelectionModel sm = excludeStrategy.exclude(cells, sumExclude);
        if ( null == sm) {
            cells = tmpCells;
            throw new ATMExcludeException();
        } else {
            return sm;
        }
    }

    public String getName() {

        return name;
    }

    public SelectionModel getState() {
        SelectionModel sm = new SelectionModel();
        for (Cell cell : cells) {
            sm.putFaceValues(cell.getNominal(), cell.getBillsCount());
        }
        return sm;
    }

    public int getTotal() {
        int total = 0;
        for (Cell cell : cells) {
            total += cell.getSumm();
        }
        return total;
    }


    public boolean isEmpty() {
        return cells.isEmpty() || getTotal() == 0;
    }

    public void putBanknotes(Nominal nominal, int numOfBanknotes) {
        for(Cell cell : cells) {
            if (cell.getNominal() == nominal) {
                cell.putFaceValues(numOfBanknotes);
                break;
            }
        }
    }

    public void putFaceValue(Nominal nominal) throws IllegalStateException {
        for(Cell cell : cells) {
            if (cell.getNominal() == nominal) {
                if (cell.getBillsCount() == cell.getSize()) {
                    throw new RuntimeException("Ячейки заполнены");
                }
                cell.putFaceValues(1);
                break;
            }
        }
    }

    private List<Cell> copyCells(List<Cell> cells) {
        List<Cell> cellsCopy = new ArrayList<>();
        for (Cell cell : cells) {
            cellsCopy.add(cell.clone());
        }
        return cellsCopy;
    }

    private Cell getCell(Nominal nominal) {
        for (Cell cell : cells) {
            if (cell.getNominal() == nominal) {
                return cell;
            }
        }
        return null;
    }


    private Memento saveMemento() {
        return new Memento(cells);
    }

    private void setMemento(Memento memento) {
        cells.clear();
        this.cells = memento.getState();
    }

    public void saveState() {
        initMemento = saveMemento();
    }

    public void restoreState() {
        setMemento(initMemento);
    }
}
