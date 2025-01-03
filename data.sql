Create Database warehouse_management
Go

use warehouse_management
Go


DROP DATABASE warehouse_management


-- Table: Product
INSERT INTO Products (ProductName, HSD, Color)
VALUES 
(N'Product A', 24.5, 'Red'),
(N'Product B', 12.0, 'Blue'),
(N'Product C', 18.0, 'Green');


-- Table: Shift
INSERT INTO Shifts (ShiftName)
VALUES 
(N'Ca sáng'),
(N'Ca chiều'),
('Ca tối');

-- Table: ProductionGroup
INSERT INTO ProductionGroups (GroupName)
VALUES 
(N'Nhóm A'),
(N'Nhóm B'),
(N'Nhóm C');

-- Table: WarehouseStaff
INSERT INTO WarehouseStaff (StaffName)
VALUES 
('KKQ1'),
('KKQ2'),
('KKQ3');



-- Table: Lot
INSERT INTO Lots (LotIDU, ProductID, ProductionTime, ExpirationDate, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID,Status)
VALUES 
('LOT001', 1, '2024-01-01', '2025-01-01', 100.50, 105.00, 4.50, 1, 1, 1,N'Xuất'),
('LOT002', 2, '2024-01-02', '2025-01-02', 200.75, 210.00, 9.25, 2, 2, 2,N'Nhập'),
('LOT003', 3, '2024-01-03', '2025-01-03', 150.25, 155.00, 4.75, 3, 3, 3,N'Xuất'),
('LOT004', 1, '2024-01-04', '2025-01-04', 110.00, 115.00, 5.00, 1, 1, 1,N'Nhập'),
('LOT005', 2, '2024-01-05', '2025-01-05', 220.50, 225.50, 5.00, 2, 2, 2,N'Xuất'),
('LOT006', 3, '2024-01-06', '2025-01-06', 175.00, 180.00, 5.00, 3, 3, 3,N'Nhập'),
('LOT007', 1, '2024-01-07', '2025-01-07', 120.75, 125.00, 4.25, 1, 1, 1,N'Xuất'),
('LOT008', 2, '2024-01-08', '2025-01-08', 230.75, 235.75, 5.00, 2, 2, 2,N'Nhập'),
('LOT009', 3, '2024-01-09', '2025-01-09', 160.50, 165.00, 4.50, 3, 3, 3,N'Xuất'),
('LOT010', 1, '2024-01-10', '2025-01-10', 105.00, 110.00, 5.00, 1, 1, 1,N'Nhập'),
('LOT011', 2, '2024-01-11', '2025-01-11', 195.00, 200.00, 5.00, 2, 2, 2,N'Xuất'),
('LOT012', 3, '2024-01-12', '2025-01-12', 170.00, 175.00, 5.00, 3, 3, 3,N'Nhập'),
('LOT013', 1, '2024-01-13', '2025-01-13', 115.50, 120.00, 4.50, 1, 1, 1,N'Xuất'),
('LOT014', 2, '2024-01-14', '2025-01-14', 210.00, 215.00, 4.10, 1, 1, 1,N'Nhập'),
('LOT015', 3, '2024-01-15', '2025-01-15', 165.00, 170.00, 5.00, 3, 3, 3,N'Xuất'),
('LOT016', 1, '2024-01-16', '2025-01-16', 125.00, 130.00, 5.00, 1, 1, 1,N'Nhập'),
('LOT017', 2, '2024-01-17', '2025-01-17', 205.00, 210.00, 5.00, 2, 2, 2,N'Xuất'),
('LOT018', 3, '2024-01-18', '2025-01-18', 180.50, 185.00, 4.50, 3, 3, 3,N'Nhập'),
('LOT019', 1, '2024-01-19', '2025-01-19', 135.50, 140.00, 4.50, 1, 1, 1,N'Xuất'),
('LOT020', 2, '2024-01-20', '2025-01-20', 215.50, 220.00, 4.50, 2, 2, 2,N'Nhập'),
('LOT021', 3, '2024-01-21', '2025-01-21', 190.75, 195.00, 4.25, 3, 3, 3,N'Xuất'),
('LOT022', 1, '2024-01-22', '2025-01-22', 140.00, 145.00, 5.00, 1, 1, 1,N'Nhập'),
('LOT023', 2, '2024-01-23', '2025-01-23', 225.00, 230.00, 5.00, 2, 2, 2,N''),
('LOT024', 3, '2024-01-24', '2025-01-24', 185.00, 190.00, 5.00, 3, 3, 3,NULL);

-- Table: Pallet
INSERT INTO Pallets ( PalletIDU, LotID)
VALUES 
('PAL001', 1),
('PAL002', 2),
('PAL003', 3),
('PAL004', 4),
('PAL005', 5),
('PAL006', 6),
('PAL007', 7),
('PAL008', 8),
('PAL009', 9),
('PAL010', 10),
('PAL011', 11),
('PAL012', 12),
('PAL013', 13),
('PAL014', 14),
('PAL015', 15),
('PAL016', 16),
('PAL017', 17),
('PAL018', 18),
('PAL019', 19),
('PAL020', 20),
('PAL021', 21),
('PAL022', 22),
('PAL023', 23),
('PAL024', 24);


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
INSERT INTO [Users] (FullName)
VALUES 
('User 1'),
('User 2'),
('User 3');

INSERT INTO Accounts (username, password, fullname, email) VALUES
('Accounts1', '123', 'Nguyễn Văn A', 'user1@example.com'),
('Accounts2', '123', 'Trần Thị B', 'user2@example.com'),
('Accounts3', '123', 'Lê Văn C', 'user3@example.com'),
('Accounts4', '123', 'Phạm Thị D', 'user4@example.com'),
('Accounts5', '123', 'Hoàng Văn E', 'user5@example.com');

-- Table: Transaction
INSERT INTO Transactions (TransactionType, Date, Customer, Staff)
VALUES 
('Export', '2024-12-01 10:00:00', 'Customer A', N'Accounts1'),
('Import', '2024-12-02 14:00:00', NULL, N'Accounts2'),
('Export', '2024-12-03 09:30:00', 'Customer B', N'Accounts3');

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
EXEC DeleteAllLots;




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
    pal.PalletID,
    l.Status
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
LEFT JOIN 
    Pallets pal ON l.LotID = pal.LotID;





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
    l.ExpirationDate,
    l.Weight,
    l.WarehouseWeight,
    l.WeightDeviation,
    ws.StaffID,
    ws.StaffName,
    pal.PalletID
FROM Lots l
JOIN Products p ON l.ProductID = p.ProductID
JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
JOIN Shifts s ON l.ShiftID = s.ShiftID
JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
LEFT JOIN Pallets pal ON pal.palletID = l.LotID
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
    a.username,
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
    Accounts a ON t.Staff = a.username
