package com.emate.shop.rpc.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Dataset<D, L> implements java.io.Serializable {

    private static final long          serialVersionUID = 253445390359658855L;

    private boolean                    success;

    private String                     message;

    private D                          data;

    private List<L>                    list;

    private PageInfo                   pageInfo;

    private Map<String, Dataset<?, ?>> datasets;

    private Map<String, List<?>>       datasetLists;

    public Dataset() {
        super();
    }

    public Dataset(String message) {
        super();
        this.message = message;
        this.success = false;
    }

    public Dataset(D data, List<L> list, PageInfo pageInfo) {
        super();
        this.success = true;
        this.data = data;
        this.list = list;
        this.pageInfo = pageInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<L> getList() {
        return list;
    }

    public void setList(List<L> list) {
        this.list = list;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Map<String, Dataset<?, ?>> getDatasets() {
        return datasets;
    }

    public void setDatasets(Map<String, Dataset<?, ?>> datasets) {
        this.datasets = datasets;
    }

    public Map<String, List<?>> getDatasetLists() {
        return datasetLists;
    }

    public void setDatasetLists(Map<String, List<?>> datasetLists) {
        this.datasetLists = datasetLists;
    }

    private void initDatasets() {
        if (Objects.isNull(this.datasets)) {
            this.datasets = new HashMap<>();
        }
    }

    private void initDatasetLists() {
        if (Objects.isNull(this.datasetLists)) {
            this.datasetLists = new HashMap<>();
        }
    }

    public void putDataset(String name, Dataset<?, ?> dataset) {
        this.initDatasets();
        this.datasets.put(name, dataset);
    }

    public void putDatasetList(String name, List<?> datasetList) {
        this.initDatasetLists();
        this.datasetLists.put(name, datasetList);
    }

}
