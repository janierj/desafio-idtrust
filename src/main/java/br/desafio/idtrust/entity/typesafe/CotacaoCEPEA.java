package br.desafio.idtrust.entity.typesafe;

import br.desafio.idtrust.entity.enumeration.Culture;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CotacaoCEPEA {

    private Dataset dataset;

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public static class Dataset {
        private Long id;

        @JsonProperty("dataset_code")
        private Culture datasetCode;

        @JsonProperty("database_code")
        private String databaseCode;

        private String name;

        private String descrition;

        @JsonProperty("refreshed_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
        private Date refreshedAt;

        @JsonProperty("newest_available_date")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
        private Date newestAvailableDate;

        @JsonProperty("oldest_available_date")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
        private Date oldestAvailableDate;

        @JsonProperty("column_names")
        private String[] columnNames;

        private String[][] data;

        private String frequency;

        private String type;

        private Boolean premium;

        private String limit;

        private String transform;

        @JsonProperty("column_index")
        private String columnIndex;

        @JsonProperty("start_date")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
        private Date starDate;

        @JsonProperty("end_date")
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
        private Date endDate;

        private String collapse;

        private String order;

        @JsonProperty("database_id")
        private String databaseId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Culture getDatasetCode() {
            return datasetCode;
        }

        public void setDatasetCode(Culture datasetCode) {
            this.datasetCode = datasetCode;
        }

        public String getDatabaseCode() {
            return databaseCode;
        }

        public void setDatabaseCode(String databaseCode) {
            this.databaseCode = databaseCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescrition() {
            return descrition;
        }

        public void setDescrition(String descrition) {
            this.descrition = descrition;
        }

        public Date getRefreshedAt() {
            return refreshedAt;
        }

        public void setRefreshedAt(Date refreshedAt) {
            this.refreshedAt = refreshedAt;
        }

        public Date getNewestAvailableDate() {
            return newestAvailableDate;
        }

        public void setNewestAvailableDate(Date newestAvailableDate) {
            this.newestAvailableDate = newestAvailableDate;
        }

        public Date getOldestAvailableDate() {
            return oldestAvailableDate;
        }

        public void setOldestAvailableDate(Date oldestAvailableDate) {
            this.oldestAvailableDate = oldestAvailableDate;
        }

        public String[] getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(String[] columnNames) {
            this.columnNames = columnNames;
        }

        public String[][] getData() {
            return data;
        }

        public void setData(String[][] data) {
            this.data = data;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getPremium() {
            return premium;
        }

        public void setPremium(Boolean premium) {
            this.premium = premium;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getTransform() {
            return transform;
        }

        public void setTransform(String transform) {
            this.transform = transform;
        }

        public String getColumnIndex() {
            return columnIndex;
        }

        public void setColumnIndex(String columnIndex) {
            this.columnIndex = columnIndex;
        }

        public Date getStarDate() {
            return starDate;
        }

        public void setStarDate(Date starDate) {
            this.starDate = starDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public String getCollapse() {
            return collapse;
        }

        public void setCollapse(String collapse) {
            this.collapse = collapse;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getDatabaseId() {
            return databaseId;
        }

        public void setDatabaseId(String databaseId) {
            this.databaseId = databaseId;
        }
    }
}
