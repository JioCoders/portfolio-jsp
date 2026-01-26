-- Database Queries & Schema Documentation

-- Current Module: User Management
-- Table: users

-- 1. Get all users
SELECT * FROM users;

-- 2. Find user by username or email
SELECT * FROM users 
WHERE username = 'admintemp' OR email = 'admintemp@jiocoders.com';

-- 3. Count users by role
SELECT role, COUNT(*) as count 
FROM users 
GROUP BY role;

-- 4. Get recently created users
SELECT username, email, created_at 
FROM users 
ORDER BY created_at DESC 
LIMIT 10;

-- 5. Update user role
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'target_user';

-- 6. Delete a user
DELETE FROM users 
WHERE username = 'old_user';

-- 7. Search users with pattern
SELECT * FROM users 
WHERE username LIKE '%temp%';
