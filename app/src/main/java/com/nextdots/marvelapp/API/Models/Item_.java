
package com.nextdots.marvelapp.API.Models;


public class Item_ {

    private String resourceURI;
    private String name;
    private String type;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item_() {
    }

    /**
     * 
     * @param resourceURI
     * @param name
     * @param type
     */
    public Item_(String resourceURI, String name, String type) {
        super();
        this.resourceURI = resourceURI;
        this.name = name;
        this.type = type;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
