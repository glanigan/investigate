dbClearResult(dbListResults(mydb)[[1]])


getRecommendation=function(appNo){
  # load library
  library(RMySQL)
  
  #Connects to MySQL database and retrieves application
  mydb = dbConnect(MySQL(),
                   user='sql8168762',
                   password='WkgUR4FXga',
                   dbname='sql8168762',
                   host='sql8.freemysqlhosting.net')
  #appNo=263
  
  SQL = paste("
              SELECT APP.PRODUCT_NO,APP.APPLICATION_DATE,APP.VALUE,APP.RISK_SCORE, PER.DOB,PER.INCOME
              FROM APPLICATION APP
              LEFT JOIN PERSON PER ON APP.APP_NO=PER.APP_NO
              WHERE APP.APP_NO=",appNo)
  
  result=dbSendQuery(mydb,SQL)
  application=fetch(result)
  print(application)
  
  #Models data
  application$APPLICATION_DATE=weekdays(as.Date(application$APPLICATION_DATE,'%Y-%m-%d'))
  application$DOB= age_calc(as.Date(application$DOB ,'%Y-%m-%d'),Sys.Date(),'years')
  
  print(application)
  
  
  #Machine learning model
  
  decisions <- list('APPROVED','REFER')
  #Returns recommendation to the matching engine
  return(recommendation)
}


age_calc <- function(dob, enddate=Sys.Date(), units='months'){
  if (!inherits(dob, "Date") | !inherits(enddate, "Date"))
    stop("Both dob and enddate must be Date class objects")
  start <- as.POSIXlt(dob)
  end <- as.POSIXlt(enddate)
  
  years <- end$year - start$year
  if(units=='years'){
    result <- ifelse((end$mon < start$mon) | 
                       ((end$mon == start$mon) & (end$mday < start$mday)),
                     years - 1, years)    
  }else if(units=='months'){
    months <- (years-1) * 12
    result <- months + start$mon
  }else if(units=='days'){
    result <- difftime(end, start, units='days')
  }else{
    stop("Unrecognized units. Please choose years, months, or days.")
  }
  return(result)
}