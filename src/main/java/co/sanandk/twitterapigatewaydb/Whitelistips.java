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
@Table(name = "WHITELISTIPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Whitelistips.findAll", query = "SELECT w FROM Whitelistips w"),
    @NamedQuery(name = "Whitelistips.findById", query = "SELECT w FROM Whitelistips w WHERE w.id = :id"),
    @NamedQuery(name = "Whitelistips.findByIp", query = "SELECT w FROM Whitelistips w WHERE w.ip = :ip"),
    @NamedQuery(name = "Whitelistips.findByTimestamp", query = "SELECT w FROM Whitelistips w WHERE w.timestamp = :timestamp")})
public class Whitelistips implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "IP")
    private String ip;
    @Size(max = 50)
    @Column(name = "TIMESTAMP")
    private String timestamp;

    public Whitelistips() {
    }

    public Whitelistips(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
        if (!(object instanceof Whitelistips)) {
            return false;
        }
        Whitelistips other = (Whitelistips) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sanandk.twitterapigatewaydb.Whitelistips[ id=" + id + " ]";
    }
    
}
