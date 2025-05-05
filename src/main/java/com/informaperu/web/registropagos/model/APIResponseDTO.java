// APIResponseDTO.java
package com.informaperu.web.registropagos.model;

import java.util.List;

public class APIResponseDTO {
    private int count;
    private String next;
    private String previous;
    private List<ClientDTO> results;

    // Getters y setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<ClientDTO> getResults() {
        return results;
    }

    public void setResults(List<ClientDTO> results) {
        this.results = results;
    }
}
