Create Database weighing_management
Go

use weighing_management
Go

-- Bảng Products
CREATE TABLE Products (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(255) NOT NULL,
    Color NVARCHAR(50)
);

-- Bảng Users
CREATE TABLE Users (
    Username varchar(50) PRIMARY KEY,
    FullName NVARCHAR(255) NOT NULL,
    Role NVARCHAR(50) NOT NULL -- Vai trò: Thủ kho, Quản lý, Nhân viên
);

-- Bảng Shifts
CREATE TABLE Shifts (
    ShiftID INT IDENTITY(1,1) PRIMARY KEY,
    ShiftName NVARCHAR(50) NOT NULL -- Ví dụ: "Ca 1", "Ca 2", ...
);

-- Bảng ProductionGroups
CREATE TABLE ProductionGroups (
    GroupID INT IDENTITY(1,1) PRIMARY KEY,
    GroupName NVARCHAR(50) NOT NULL -- Ví dụ: "Tổ 1", "Tổ 2", ...
);

-- Bảng WarehouseStaff 
CREATE TABLE WarehouseStaff (
    StaffID INT PRIMARY KEY, -- Trùng với UserID trong bảng Users
	StaffName NVARCHAR(50) NOT NULL 
);

-- Bảng Lots 
CREATE TABLE Lots (
	LotID INT IDENTITY(1,1) PRIMARY KEY,
    LotIDU char(20) UNIQUE ,
    ProductID INT NOT NULL, -- Khóa ngoại đến bảng Products
    ProductionTime DATETIME NOT NULL, -- Thời gian sản xuất
    ExpirationDays INT NOT NULL, -- Hạn sử dụng
    Weight DECIMAL(10,2) NOT NULL, -- Khối lượng tịnh
    WarehouseWeight DECIMAL(10,2) NOT NULL, -- Khối lượng cân
    WeightDeviation DECIMAL(10,2) NOT NULL, -- Khối lượng bì
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
);

-- Cập nhật bảng Lots
ALTER TABLE Lots
ADD ShiftID INT NOT NULL, -- Tham chiếu đến bảng Shifts
    GroupID INT NOT NULL, -- Tham chiếu đến bảng ProductionGroups
	WarehouseStaff INT NOT NULL, -- Tham chiếu đến bảng ProductionGroups
    FOREIGN KEY (ShiftID) REFERENCES Shifts(ShiftID),
    FOREIGN KEY (GroupID) REFERENCES ProductionGroups(GroupID),
	FOREIGN KEY (WarehouseStaff) REFERENCES WarehouseStaff(StaffID);


-- Bảng Transactions
CREATE TABLE Transactions (
    TransactionID INT IDENTITY(1,1) PRIMARY KEY,
    TransactionType NVARCHAR(50) NOT NULL, -- "Xuất" hoặc "Nhập"
    Date DATETIME NOT NULL,
    Customer NVARCHAR(255), -- Chỉ áp dụng cho "Xuất"
    Staff varchar(50) NOT NULL,
    FOREIGN KEY (Staff) REFERENCES Users(Username)
);

-- Bảng TransactionDetails
CREATE TABLE TransactionDetails (
    DetailID INT IDENTITY(1,1) PRIMARY KEY,
    TransactionID INT NOT NULL,
    LotID INT  NOT NULL,
    Quantity DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (TransactionID) REFERENCES Transactions(TransactionID),
    FOREIGN KEY (LotID) REFERENCES Lots(LotID)
);

-- Bảng Pallets
CREATE TABLE Pallets (
    PalletID INT IDENTITY(1,1) PRIMARY KEY,
    LotID INT  NOT NULL,
    FOREIGN KEY (LotID) REFERENCES Lots(LotID)
);
