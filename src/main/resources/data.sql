insert into drones (id, serial_number, model, battery_capacity, state, state_changed_at) values
('11111111-1111-1111-1111-111111111111', 'DRONE-20260712-001', 'LIGHTWEIGHT', 95, 'IDLE', current_timestamp),
('22222222-2222-2222-2222-222222222222', 'DRONE-20260712-002', 'MIDDLEWEIGHT', 88, 'LOADING', current_timestamp),
('33333333-3333-3333-3333-333333333333', 'DRONE-20260712-003', 'CRUISERWEIGHT', 76, 'LOADING', current_timestamp),
('44444444-4444-4444-4444-444444444444', 'DRONE-20260712-004', 'HEAVYWEIGHT', 70, 'LOADED', current_timestamp),
('55555555-5555-5555-5555-555555555555', 'DRONE-20260712-005', 'LIGHTWEIGHT', 65, 'DELIVERING', current_timestamp),
('66666666-6666-6666-6666-666666666666', 'DRONE-20260712-006', 'MIDDLEWEIGHT', 58, 'DELIVERED', current_timestamp),
('77777777-7777-7777-7777-777777777777', 'DRONE-20260712-007', 'CRUISERWEIGHT', 49, 'RETURNING', current_timestamp),
('88888888-8888-8888-8888-888888888888', 'DRONE-20260712-008', 'HEAVYWEIGHT', 40, 'IDLE', current_timestamp),
('99999999-9999-9999-9999-999999999999', 'DRONE-20260712-009', 'LIGHTWEIGHT', 92, 'LOADING', current_timestamp),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'DRONE-20260712-010', 'HEAVYWEIGHT', 61, 'LOADED', current_timestamp);

insert into medications (id, name, weight, code, image) values
('aaaaaaaa-0000-0000-0000-000000000001', 'PainRelief', 125, 'MED_001', 'https://example.com/images/med_001.png'),
('aaaaaaaa-0000-0000-0000-000000000002', 'Antibiotic', 200, 'MED_002', 'https://example.com/images/med_002.png'),
('aaaaaaaa-0000-0000-0000-000000000003', 'BandageKit', 75, 'MED_003', 'https://example.com/images/med_003.png'),
('aaaaaaaa-0000-0000-0000-000000000004', 'Insulin', 50, 'MED_004', 'https://example.com/images/med_004.png'),
('aaaaaaaa-0000-0000-0000-000000000005', 'Vaccine', 300, 'MED_005', 'https://example.com/images/med_005.png'),
('aaaaaaaa-0000-0000-0000-000000000006', 'Saline', 150, 'MED_006', 'https://example.com/images/med_006.png'),
('aaaaaaaa-0000-0000-0000-000000000007', 'Analgesic', 100, 'MED_007', 'https://example.com/images/med_007.png'),
('aaaaaaaa-0000-0000-0000-000000000008', 'Antiseptic', 175, 'MED_008', 'https://example.com/images/med_008.png'),
('aaaaaaaa-0000-0000-0000-000000000009', 'SyringePack', 225, 'MED_009', 'https://example.com/images/med_009.png'),
('aaaaaaaa-0000-0000-0000-000000000010', 'Glucose', 90, 'MED_010', 'https://example.com/images/med_010.png');

insert into drone_medications (id, drone_id, medication_id) values
('bbbbbbbb-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 'aaaaaaaa-0000-0000-0000-000000000001'),
('bbbbbbbb-0000-0000-0000-000000000002', '33333333-3333-3333-3333-333333333333', 'aaaaaaaa-0000-0000-0000-000000000002'),
('bbbbbbbb-0000-0000-0000-000000000003', '44444444-4444-4444-4444-444444444444', 'aaaaaaaa-0000-0000-0000-000000000003'),
('bbbbbbbb-0000-0000-0000-000000000004', '55555555-5555-5555-5555-555555555555', 'aaaaaaaa-0000-0000-0000-000000000004'),
('bbbbbbbb-0000-0000-0000-000000000005', '66666666-6666-6666-6666-666666666666', 'aaaaaaaa-0000-0000-0000-000000000005'),
('bbbbbbbb-0000-0000-0000-000000000006', '99999999-9999-9999-9999-999999999999', 'aaaaaaaa-0000-0000-0000-000000000006'),
('bbbbbbbb-0000-0000-0000-000000000007', '99999999-9999-9999-9999-999999999999', 'aaaaaaaa-0000-0000-0000-000000000007'),
('bbbbbbbb-0000-0000-0000-000000000008', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaaa-0000-0000-0000-000000000008'),
('bbbbbbbb-0000-0000-0000-000000000009', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaaa-0000-0000-0000-000000000009'),
('bbbbbbbb-0000-0000-0000-000000000010', '77777777-7777-7777-7777-777777777777', 'aaaaaaaa-0000-0000-0000-000000000010');
