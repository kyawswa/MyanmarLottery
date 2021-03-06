/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksa.myanmarlottery.model;

import com.ksa.myanmarlottery.util.ConstantUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author kyawswar
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "result.findLatestResult",
        query = "from Result r where r.resultFor in (select max(rr.resultFor) from Result rr where rr.type = :type)"
    ),
    @NamedQuery(
        name = "result.findLatestResultSummary",
        query = "Select r.numberOfTimes as numberOfTimes, r.resultFor as resultFor, r.type as type from Result r where r.type = :type"
    ),
    @NamedQuery(
        name = "result.findByType",
        query = "from Result r where r.type = :type"
    )
})
@Table(name = "Lottery_result")
public class Result implements Serializable {
    
    @Id
    @Column(name = "RESULT_TYPE")
    private int type = ConstantUtil.OLD_LOTTERY_TYPE;;
    
    private int numberOfTimes;
    
    @Temporal(TemporalType.DATE)
    private Date createdDate = new Date();
    
    @Temporal(TemporalType.DATE)
    private Date resultFor = new Date();
    
    private String dataProvider;
    
    private String companyName;

    @OneToMany(mappedBy = "result",fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<Prize> prizes = new ArrayList<Prize>();
    
    public Result() {
    }

    public Result(int type, int numberOfTimes, Date createdDate, Date resultFor) {
        this.type = type;
        this.numberOfTimes = numberOfTimes;
        this.resultFor = resultFor;
    }
    
    public Result(int numberOfTimes, Date resultFor, int type) {
        this.numberOfTimes = numberOfTimes;
        this.resultFor = resultFor;
        this.type = type;
    }
    
    public int getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getResultFor() {
        return resultFor;
    }

    public void setResultFor(Date resultFor) {
        this.resultFor = resultFor;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(String dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Result{" +"numberOfTimes=" + numberOfTimes + ", createdDate=" + createdDate + ", type=" + type + ", resultFor=" + resultFor + ", prizes=" + prizes + '}';
    }
}
