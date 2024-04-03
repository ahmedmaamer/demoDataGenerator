        package com.example.demo.model;

        import org.springframework.data.util.Pair;

        import java.util.*;

        public class AttributeGenerationRequest {
            private String attributeName;
            private String dataType;
            private int minValue;
            private int maxValue;
            private int stringLength; // Additional parameter for string type
            private int dataPoints;
            private String generationMethod; // Method for generating attribute data

            private List<Integer> specifiedValues; // Additional parameter for some generation methods
            private List<String > specifiedDates ;
            private Date firstDate ;
            private Date lastDate ;

            public Date getFirstDate() {
                return firstDate;
            }

            public void setFirstDate(Date firstDate) {
                this.firstDate = firstDate;
            }

            public Date getLastDate() {
                return lastDate;
            }

            public void setLastDate(Date lastDate) {
                this.lastDate = lastDate;
            }

            public List<String > getSpecifiedDates() {
                return specifiedDates;
            }

            public void setSpecifiedDates(List<String > specifiedDates) {
                this.specifiedDates = specifiedDates;
            }

            private List<Pair<Integer, Integer>> ranges; // Additional parameter for some generation methods
            private String dateGenerationMethod ;

            public String getDateGenerationMethod() {
                return dateGenerationMethod;
            }

            public void setDateGenerationMethod(String dateGenerationMethod) {
                this.dateGenerationMethod = dateGenerationMethod;
            }

            private String stringGenerationMethod ;

            public String getStringGenerationMethod() {
                return stringGenerationMethod;
            }

            public void setStringGenerationMethod(String stringGenerationMethod) {
                this.stringGenerationMethod = stringGenerationMethod;
            }

            private List<String> specifiedStringValues; // New attribute for string values
            private String pattern; // New attribute for pattern

            public String getPattern() {
                return pattern;
            }

            public void setPattern(String pattern) {
                this.pattern = pattern;
            }

            public List<String> getSpecifiedStringValues() {
                return specifiedStringValues;
            }

            public void setSpecifiedStringValues(List<String> specifiedStringValues) {
                this.specifiedStringValues = specifiedStringValues;
            }

            public List<Integer> getSpecifiedValues() {
                return specifiedValues;
            }



            public String getAttributeName() {
                return attributeName;
            }

            public void setAttributeName(String attributeName) {
                this.attributeName = attributeName;
            }

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public int getMinValue() {
                return minValue;
            }

            public void setMinValue(int minValue) {
                this.minValue = minValue;
            }

            public int getMaxValue() {
                return maxValue;
            }

            public void setMaxValue(int maxValue) {
                this.maxValue = maxValue;
            }

            public int getStringLength() {
                return stringLength;
            }

            public void setStringLength(int stringLength) {
                this.stringLength = stringLength;
            }

            public int getDataPoints() {
                return dataPoints;
            }

                     public void setSpecifiedValues(List<Integer> specifiedValues) {
                this.specifiedValues = specifiedValues;
            }


            public List<Pair<Integer, Integer>> getRanges() {
                return ranges;
            }

            public void setRanges(List<Pair<Integer, Integer>> ranges) {
                this.ranges = ranges;
            }

            public String getGenerationMethod() {
                return generationMethod;
            }

            public void setGenerationMethod(String generationMethod) {
                this.generationMethod = generationMethod;
            }
        }

