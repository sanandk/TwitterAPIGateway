/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAnanda
 */
@Entity
@Table(name = "LOGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Logs l"),
    @NamedQuery(name = "Logs.findById", query = "SELECT l FROM Logs l WHERE l.id = :id"),
    @NamedQuery(name = "Logs.findByEndpointname", query = "SELECT l FROM Logs l WHERE l.endpointname = :endpointname"),
    @NamedQuery(name = "Logs.findBySuccess", query = "SELECT l FROM Logs l WHERE l.success = :success"),
    @NamedQuery(name = "Logs.findByError", query = "SELECT l FROM Logs l WHERE l.error = :error"),
    @NamedQuery(name = "Logs.findByTimestamp", query = "SELECT l FROM Logs l WHERE l.timestamp = :timestamp"),
    @NamedQuery(name = "Logs.findByIp", query = "SELECT l FROM Logs l WHERE l.ip = :ip")})
public class Logs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 100)
    @Column(name = "ENDPOINTNAME")
    private String endpointname;
    @Column(name = "SUCCESS")
    private Integer success;
    @Size(max = 200)
    @Column(name = "ERROR")
    private String error;
    @Size(max = 50)
    @Column(name = "TIMESTAMP")
    private String timestamp;
    @Size(max = 50)
    @Column(name = "IP")
    private String ip;

    public Logs() {
    }

    public Logs(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpointname() {
        return endpointname;
    }

    public void setEndpointname(String endpointname) {
        this.endpointname = endpointname;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logs)) {
            return false;
        }
        Logs other = (Logs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sanandk.twitterapigatewaydb.Logs[ id=" + id + " ]";
    }
    
}
