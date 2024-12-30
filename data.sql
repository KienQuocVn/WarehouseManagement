Create Database warehouse_management
Go

use warehouse_management
Go


DROP DATABASE warehouse_management

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


INSERT INTO Products ( ProductName, HSD, Color) VALUES
('Sản phẩm A', 12.50, 'Đỏ'),
('Sản phẩm B', 24.75, 'Xanh'),
('Sản phẩm C', 18.00, 'Vàng'),
('Sản phẩm D', 36.25, 'Trắng'),
('Sản phẩm E', 48.00, 'Đen');

SELECT * FROM Products
SELECT ProductID FROM Products WHERE ProductID = 1;



INSERT INTO ProductionGroups( GroupName) VALUES
('Nhóm A'),
('Nhóm B'),
('Nhóm C');


INSERT INTO Shifts ( ShiftName) VALUES
('Ca sáng'),
('Ca chiều'),
('Ca đêm');

INSERT INTO WarehouseStaff ( StaffName) VALUES
('Nguyễn Văn A'),
('Trần Thị B'),
('Lê Văn C');

INSERT INTO Pallets(PalletName) VALUES
('PalletsName1'),
('PalletsName2'),
('PalletsName3'),
('PalletsName4'),
('PalletsName5'),
('PalletsName6'),
('PalletsName7'),
('PalletsName8'),
('PalletsName9'),
('PalletsName10');

INSERT INTO Lots (LotIDU, ProductID, ProductionTime, ExpirationDays, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID,PalletID) VALUES
('LOT001', 1, '2024-01-01', '2024-06-30', 100.50, 105.00, 04.50, 1, 1, 1,1),
('LOT002', 2, '2024-02-01', '2024-07-31', 200.75, 210.00, 09.25, 2, 2, 2,2),
('LOT003', 3, '2024-03-01', '2024-08-31', 300.25, 315.00, 14.75, 3, 3, 3,3),
('LOT004', 4, '2024-04-01', '2024-09-30', 400.00, 420.00, 20.00, 1, 1, 1,4),
('LOT005', 5, '2024-05-01', '2024-10-31', 500.50, 525.00, 24.50, 2, 2, 2,5),
('LOT006', 1, '2024-06-01', '2024-11-30', 600.25, 630.00, 29.75, 3, 3, 3,6),
('LOT007', 2, '2024-07-01', '2024-12-31', 700.75, 735.00, 34.25, 1, 1, 1,7),
('LOT008', 3, '2024-08-01', '2025-01-31', 800.00, 840.00, 40.00, 2, 2, 2,8),
('LOT009', 4, '2024-09-01', '2025-02-28', 900.50, 945.00, 44.50, 3, 3, 3,9),
('LOT010', 5, '2024-10-01', '2025-03-31', 100.75, 105.00, 49.25, 1, 1, 1,10);


SELECT *FROM Lots


UPDATE Lots
SET 
    ProductID = (SELECT ProductID FROM Products WHERE ProductName = N'Sản phẩm C'),
    GroupID = (SELECT GroupID FROM ProductionGroups WHERE GroupName = N'Nhóm C'),
    ShiftID = (SELECT ShiftID FROM Shifts WHERE ShiftName = N'Ca sáng'),
    ProductionTime = '2024-02-08',
    ExpirationDays = '2024-02-10',
    Weight = 66666,
    WarehouseWeight = 555555555.00,
    WeightDeviation = 444444444.00,
    WarehouseStaffID = (SELECT WarehouseStaffID FROM WarehouseStaff WHERE StaffName = N'Lê Văn C'),
    PalletID = 2
WHERE 
    LotID = 11;


INSERT INTO Users(Username, FullName, Role) VALUES
('user1', 'Nguyễn Văn A', 'Thủ kho'),
('user2', 'Trần Thị B', 'Quản lý'),
('user3', 'Lê Văn C', 'Nhân viên');

INSERT INTO Transactions (TransactionType, Date, Customer, Staff)
VALUES
('Nhập', '2024-01-01 10:00:00', NULL, 'user1'),
('Xuất', '2024-02-01 11:00:00', 'Công ty A', 'user2'),
('Nhập', '2024-03-01 12:00:00', NULL, 'user3');

INSERT INTO TransactionDetails (TransactionID, LotID, Quantity) VALUES
(1, 1, 50.00),
(1, 2, 100.00),
(1, 3, 150.00);


CREATE PROCEDURE DeleteLot
    @LotID INT
AS
BEGIN
    -- Xóa dữ liệu từ TransactionDetails
    DELETE FROM TransactionDetails WHERE LotID = @LotID;

    -- Xóa dữ liệu từ Lots
    DELETE FROM Lots WHERE LotID = @LotID;
END;
GO






/*Select ALL*/

SELECT 
    l.LotID,
    l.LotIDU,
    p.ProductID,
    p.ProductName,
    pg.GroupID,
    pg.GroupName,
    s.ShiftID,
    s.ShiftName,
    l.ProductionTime,
    l.ExpirationDays,
    l.Weight,
    l.WarehouseWeight,
    l.WeightDeviation,
    ws.StaffID,
    ws.StaffName,
	pal.PalletID
FROM 
    Lots l
JOIN 
    Products p ON l.ProductID = p.ProductID
JOIN 
    ProductionGroups pg ON l.GroupID = pg.GroupID
JOIN 
    Shifts s ON l.ShiftID = s.ShiftID
JOIN 
    WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
JOIN Pallets pal ON l.LotID = pal.palletID	;




/*Search*/
SELECT 
    l.LotID,
    l.LotIDU,
    p.ProductID,
    p.ProductName,
    pg.GroupID,
    pg.GroupName,
    s.ShiftID,
    s.ShiftName,
    l.ProductionTime,
    l.ExpirationDays,
    l.Weight,
    l.WarehouseWeight,
    l.WeightDeviation,
    ws.StaffID,
    ws.StaffName,
    pal.PalletID,
	pal.PalletName
FROM Lots l
JOIN Products p ON l.ProductID = p.ProductID
JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
JOIN Shifts s ON l.ShiftID = s.ShiftID
JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
LEFT JOIN Pallets pal ON pal.palletID = l.palletID
WHERE (pg.GroupName = 'Nhóm A')
  AND (s.ShiftName = 'Ca sáng')
  AND (p.ProductName = 'Sản phẩm A')
  AND (l.ProductionTime >= '2024-01-01' OR '2024-01-01' IS NULL)
  AND (l.ProductionTime <= '2024-12-31' OR '2024-12-31' IS NULL)
ORDER BY l.ProductionTime DESC;


