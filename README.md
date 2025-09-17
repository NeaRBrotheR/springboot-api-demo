技術框架與工具:
1.Java 17（OpenJDK）
2.Spring Boot 3.x
  |-Spring Web (建立 RESTful API)
  |-Spring Data JPA (資料存取)
  |-H2 Database (輕量內嵌資料庫)

3.Maven 3.8+（建置與依賴管理）
4.GitHub Codespaces（雲端開發環境，VS Code）
5.cURL（命令列測試 API）

系統架構：
資料表設計
 |- users：紀錄使用者基本資料（name, email, adress, sex）
 |- accounts：紀錄帳號登入資料（nickname, password）並透過 One-to-One 關聯連結至 users

 API 設計：
  |- 1. /api/users：提供查詢、刪除、條件搜尋（email, name, adress, sex）
  |- 2. /api/accounts：提供帳號建立、查詢（id, nickname）、批次新增

###
users 與 accounts 綁定，只有accounts可以建立/移除資料，同時也會替users建立/移除配對的資料
###

資料持久化：
  |- H2 以 檔案模式 儲存 (./data/demo-db.mv.db)，確保重啟後資料不會遺失。
  (檔案路徑為 ./data/demo-db.mv.db，重啟後資料仍保留)

部屬與啟動步驟：

1.下載或 clone 專案：
   git clone https://github.com/NeaRBrotheR/springboot-api-demo.git
   cd /springboot-api-demo

2.使用 Maven 啟動 Spring Boot 專案：
   mvn spring-boot:run
   或使用 mvn clean package 打包後執行 java -jar target/demo-0.0.1-SNAPSHOT.jar
   
3.服務啟動後，API 預設運行於：
   http://localhost:8080
   
4.建立單筆資料：
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "playerOne",
    "password": "pw12345",
    "user": {
      "name": "Alice Chen",
      "email": "alice@example.com",
      "adress": "Taipei",
      "sex": "female"
    }
  }'

5.建立多筆資料：
使用根目錄已建立好的 accounts.json (裡面已經有內容，採用json list)
curl -X POST http://localhost:8080/api/accounts/batch \
  -H "Content-Type: application/json" \
  -d @accounts.json

測試 API 範例：
1.查詢所有帳號： curl http://localhost:8080/api/accounts
2.查詢單一帳號（依 ID）： curl http://localhost:8080/api/accounts/{id}
3.查詢單一帳號（依暱稱）： curl http://localhost:8080/api/accounts/by-nickname/{nickname}
4.刪除帳號（同時刪除對應的 User）： curl -X DELETE http://localhost:8080/api/accounts/{id}

/api/users/by-{item} 包含查詢透過性別、電子郵件、名字、地址
sex、email、name、address

/api/users
  |- GET 使用者資料
  |- GET {id}：查單一使用者（依 ID）
  |- GET /by-nickname/{sex}：使用者資料（依性別）
  |- GET /by-nickname/{email}：使用者資料（依電子郵件）
  |- GET /by-nickname/{name}：使用者資料（依名字）
  |- GET /by-nickname/{address}：使用者資料（依地址）

範例：
curl "http://localhost:8080/api/users/by-name/Charlie%20Wang"
curl "http://localhost:8080/api/users/by-email/bob%40example.com"

/api/accounts
  |- GET 所有帳號
  |- GET {id}：查單一帳號（依 ID）
  |- GET /by-nickname/{nickname}：查帳號（依暱稱）
  |- POST：建立單筆帳號
  |- POST /batch：建立多筆帳號
  |- DELETE {id}：刪除帳號（同時刪除對應 User）
