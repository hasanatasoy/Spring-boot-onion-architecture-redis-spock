package com.trendyol.basket.model;

public class ApiResponse<Data> {
    private boolean isSuccess;
    private Data data;
    private int statusCode = 200;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ApiResponse(){}

    public static class ApiResponseBuilderWithData<Data> {
        private boolean isSuccess = true;
        private Data data;
        private int statusCode = 200;
        private String message;

        private ApiResponseBuilderWithData(){}

        public static <Data> ApiResponseBuilderWithData builder(){
            return new ApiResponseBuilderWithData<Data>();
        }

        public ApiResponseBuilderWithData setSuccess(boolean success) {
            isSuccess = success;
            return this;
        }

        public ApiResponseBuilderWithData setData(Data data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilderWithData setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ApiResponseBuilderWithData setMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponse<Data> build(){
            var apiResponse = new ApiResponse<Data>();
            apiResponse.data = this.data;
            apiResponse.statusCode = this.statusCode;
            apiResponse.isSuccess = this.isSuccess;
            apiResponse.message = this.message;
            return apiResponse;
        }
    }

    public static class ApiResponseBuilder {
        private boolean isSuccess = true;
        private int statusCode;
        private String message;

        private ApiResponseBuilder(){}

        public static ApiResponseBuilder builder(){
            return new ApiResponseBuilder();
        }

        public ApiResponseBuilder setSuccess(boolean success) {
            isSuccess = success;
            return this;
        }

        public ApiResponseBuilder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ApiResponseBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponse build(){
            var apiResponse = new ApiResponse();
            apiResponse.statusCode = this.statusCode;
            apiResponse.isSuccess = this.isSuccess;
            apiResponse.message = this.message;
            return apiResponse;
        }
    }
}
