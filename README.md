# ClassMediator CSV Processing
Demo project to demonstrate how to use ClassMediator to process CSV files in WSO2 Integration Studio

### How to run the example

1. Start WSO2 Integration Studio ([Installing WSO2 Integration Studio](https://ei.docs.wso2.com/en/latest/micro-integrator/develop/installing-WSO2-Integration-Studio/)).
2. In your menu in Studio, click the **File** menu. In the File menu select the **Import...** item.
3. In the Import window select the **Existing WSO2 Projects into workspace** under **WSO2** folder. And select cloned repo directory.
4. Run the sample by right click on the **ClassMediatorCSVCompositeApplication** project and selecting **Export Project Artifacts and Run**.
10. Send a POST request to [localhost:8290/csv](localhost:8290/csv) with a CSV content. Example CURL request is as bellow

``` curl
curl --location --request POST 'localhost:8290/csv' \
--header 'Content-Type: text/plain' \
--data-raw 'id,name
1,mark
2,sam'
```