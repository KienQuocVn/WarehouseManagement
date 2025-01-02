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


select*from Products

-- Table: Product
INSERT INTO Products (ProductName, HSD, Color)
VALUES 
('Product A', 24.5, 'Red'),
('Product B', 12.0, 'Blue'),
('Product C', 18.0, 'Green');

SELECT LotIDU FROM Lots WHERE ProductID = 1

-- Table: Shift
INSERT INTO Shifts (ShiftName)
VALUES 
('Morning Shift'),
('Evening Shift'),
('Night Shift');

-- Table: ProductionGroup
INSERT INTO ProductionGroups (GroupName)
VALUES 
('Group Alpha'),
('Group Beta'),
('Group Gamma');

-- Table: WarehouseStaff
INSERT INTO WarehouseStaff (StaffName)
VALUES 
('John Doe'),
('Jane Smith'),
('Mike Johnson');

--Dem Identity LotID
SELECT IDENT_CURRENT('Lots') AS CurrentIdentity, 
       IDENT_INCR('Lots') AS IncrementStep,
       IDENT_CURRENT('Lots') + IDENT_INCR('Lots') AS NextIdentity;


-- Table: Lot
INSERT INTO Lots (LotIDU, ProductID, ProductionTime, ExpirationDate, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID)
VALUES 
('LOT001', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT002', 2, '2024-01-02', '2025-01-02', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT003', 3, '2024-01-03', '2025-01-03', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT004', 1, '2024-01-04', '2025-01-04', 110.00, 115.00, 5.00, 1, 1, 1),
('LOT005', 2, '2024-01-05', '2025-01-05', 220.50, 225.50, 5.00, 2, 2, 2),
('LOT006', 3, '2024-01-06', '2025-01-06', 175.00, 180.00, 5.00, 3, 3, 3),
('LOT007', 1, '2024-01-07', '2025-01-07', 120.75, 125.00, 4.25, 1, 1, 1),
('LOT008', 2, '2024-01-08', '2025-01-08', 230.75, 235.75, 5.00, 2, 2, 2),
('LOT009', 3, '2024-01-09', '2025-01-09', 160.50, 165.00, 4.50, 3, 3, 3),
('LOT010', 1, '2024-01-10', '2025-01-10', 105.00, 110.00, 5.00, 1, 1, 1),
('LOT011', 2, '2024-01-11', '2025-01-11', 195.00, 200.00, 5.00, 2, 2, 2),
('LOT012', 3, '2024-01-12', '2025-01-12', 170.00, 175.00, 5.00, 3, 3, 3),
('LOT013', 1, '2024-01-13', '2025-01-13', 115.50, 120.00, 4.50, 1, 1, 1),
('LOT014', 2, '2024-01-14', '2025-01-14', 210.00, 215.00, 4.10, 1, 1, 1),
('LOT015', 3, '2024-01-15', '2025-01-15', 165.00, 170.00, 5.00, 3, 3, 3),
('LOT016', 1, '2024-01-16', '2025-01-16', 125.00, 130.00, 5.00, 1, 1, 1),
('LOT017', 2, '2024-01-17', '2025-01-17', 205.00, 210.00, 5.00, 2, 2, 2),
('LOT018', 3, '2024-01-18', '2025-01-18', 180.50, 185.00, 4.50, 3, 3, 3),
('LOT019', 1, '2024-01-19', '2025-01-19', 135.50, 140.00, 4.50, 1, 1, 1),
('LOT020', 2, '2024-01-20', '2025-01-20', 215.50, 220.00, 4.50, 2, 2, 2),
('LOT021', 3, '2024-01-21', '2025-01-21', 190.75, 195.00, 4.25, 3, 3, 3),
('LOT022', 1, '2024-01-22', '2025-01-22', 140.00, 145.00, 5.00, 1, 1, 1),
('LOT023', 2, '2024-01-23', '2025-01-23', 225.00, 230.00, 5.00, 2, 2, 2),
('LOT024', 3, '2024-01-24', '2025-01-24', 185.00, 190.00, 5.00, 3, 3, 3);


-- Table: Pallet
INSERT INTO Pallets (PalletName, PalletIDU, LotID)
VALUES 
('Pallet A1', 'PAL001', 1),
('Pallet A2', 'PAL002', 2),
('Pallet A3', 'PAL003', 3),
('Pallet B1', 'PAL004', 4),
('Pallet B2', 'PAL005', 5),
('Pallet B3', 'PAL006', 6),
('Pallet C1', 'PAL007', 7),
('Pallet C2', 'PAL008', 8),
('Pallet C3', 'PAL009', 9),
('Pallet D1', 'PAL010', 10),
('Pallet D2', 'PAL011', 11),
('Pallet D3', 'PAL012', 12),
('Pallet E1', 'PAL013', 13),
('Pallet E2', 'PAL014', 14),
('Pallet E3', 'PAL015', 15),
('Pallet F1', 'PAL016', 16),
('Pallet F2', 'PAL017', 17),
('Pallet F3', 'PAL018', 18),
('Pallet G1', 'PAL019', 19),
('Pallet G2', 'PAL020', 20),
('Pallet G3', 'PAL021', 21),
('Pallet H1', 'PAL022', 22),
('Pallet H2', 'PAL023', 23),
('Pallet H3', 'PAL024', 24);


-- Table: SerialPortConfig
INSERT INTO SerialPortConfig (comPort, baudRate, dataBits, stopBits, parityBits)
VALUES 
('COM1', 9600, 8, 1, 'None'),
('COM2', 115200, 8, 1, 'Even'),
('COM3', 4800, 7, 2, 'Odd');

-- Table: SettingSystem
INSERT INTO SettingSystem (DirSave, NameFile, WeightThreshold, Printer)
VALUES 
('C:\\Data', 'Report.pdf', 50.00, 'Printer A'),
('D:\\Exports', 'ExportData.xlsx', 100.00, 'Printer B'),
('E:\\Logs', 'Log.txt', 25.00, 'Printer C');

-- Table: User
INSERT INTO [Users] (Username, FullName, Role)
VALUES 
('john_doe', 'John Doe', 'Manager'),
('jane_smith', 'Jane Smith', 'Warehouse Staff'),
('mike_johnson', 'Mike Johnson', 'Employee');

-- Table: Transaction
INSERT INTO Transactions (TransactionType, Date, Customer, Staff)
VALUES 
('Export', '2024-12-01 10:00:00', 'Customer A', 'john_doe'),
('Import', '2024-12-02 14:00:00', NULL, 'jane_smith'),
('Export', '2024-12-03 09:30:00', 'Customer B', 'mike_johnson');

-- Table: TransactionDetail
INSERT INTO TransactionDetails (TransactionID, LotID, Quantity)
VALUES 
(1, 1, 50.00),
(2, 2, 100.00),
(3, 3, 75.00);






/*Delete Id*/
CREATE PROCEDURE DeleteLot
    @LotID INT
AS
BEGIN
	-- Xóa dữ liệu từ Pallets liên kết với Lot
     DELETE FROM Pallets WHERE LotID = @LotID;

    -- Xóa dữ liệu từ TransactionDetails
    DELETE FROM TransactionDetails WHERE LotID = @LotID;

    -- Xóa dữ liệu từ Lots
    DELETE FROM Lots WHERE LotID = @LotID;
END;
GO

/*Delete All*/
CREATE PROCEDURE DeleteAllLots
AS
BEGIN
    -- Xóa toàn bộ dữ liệu từ Pallets
    DELETE FROM Pallets;

    -- Xóa toàn bộ dữ liệu từ TransactionDetails
    DELETE FROM TransactionDetails;

    -- Xóa toàn bộ dữ liệu từ Lots
    DELETE FROM Lots;
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
    l.ExpirationDate,
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


SELECT ws.StaffName
FROM Products p
JOIN Lots l ON p.productID = l.ProductID
JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.staffId
WHERE p.ProductName = N'Product A';


SELECT 
    l.LotID,
    u.Username,
    t.Date,
    ws.StaffName
FROM 
    TransactionDetails td
JOIN 
    Lots l ON td.LotID = l.LotID
JOIN 
    Transactions t ON td.TransactionID = t.TransactionID
JOIN 
    WarehouseStaff ws ON l.WarehouseStaffID = ws.staffId
JOIN 
    [Users] u ON t.Staff = u.Username
