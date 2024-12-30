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

-- Table: Lot
INSERT INTO Lots (LotIDU, ProductID, ProductionTime, ExpirationDate, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID)
VALUES 
('LOT001', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT002', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT003', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT004', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT005', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT006', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT007', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT008', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT009', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT0010', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT0011', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT0012', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT0013', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT0014', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT0015', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT0016', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT0017', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT0018', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT0019', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT0020', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT0021', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3),
('LOT0022', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1),
('LOT0023', 2, '2024-02-01', '2025-02-01', 200.75, 210.00, 9.25, 2, 2, 2),
('LOT0024', 3, '2024-03-01', '2025-03-01', 150.25, 155.00, 4.75, 3, 3, 3);

-- Table: Pallet
INSERT INTO Pallets (PalletName, PalletIDU, LotID)
VALUES 
('Pallet A1', 'PAL001', 11),
('Pallet B1', 'PAL002', 12),
('Pallet C1', 'PAL003', 13);

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
(2, 1, 50.00),
(3, 2, 100.00),
(4, 3, 75.00);




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


