DROP DATABASE IF EXISTS tienda_huerto_hogar;
CREATE DATABASE tienda_huerto_hogar CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE tienda_huerto_hogar;

-- ==========================================================
--  TABLA: ROLES
-- ==========================================================
CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  TABLA: USUARIOS
-- ==========================================================
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  telefono VARCHAR(20),
  direccion VARCHAR(255),
  rol_id BIGINT NOT NULL,
  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
-- TABLA: CATEGORIAS
-- ==========================================================
CREATE TABLE categorias (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL UNIQUE,
  descripcion TEXT,
  imagen VARCHAR(255),
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  TABLA: PRODUCTOS
-- ==========================================================
CREATE TABLE productos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(50) UNIQUE NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  precio DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  origen VARCHAR(100),
  categoria_id BIGINT,
  imagen_url VARCHAR(255),
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  TABLA: CARRITOS
-- ==========================================================
CREATE TABLE carritos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  activo BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  TABLA: DETALLE_CARRITO
-- ==========================================================
CREATE TABLE detalle_carrito (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  carrito_id BIGINT NOT NULL,
  producto_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (carrito_id) REFERENCES carritos(id) ON DELETE CASCADE,
  FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  TABLA: PEDIDOS
-- ==========================================================
CREATE TABLE pedidos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
  estado VARCHAR(50) DEFAULT 'PENDIENTE',
  total DECIMAL(10,2) NOT NULL,
  direccion_entrega VARCHAR(255),
  telefono_contacto VARCHAR(20),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_pedidos_estado ON pedidos(estado);
CREATE INDEX idx_pedidos_usuario ON pedidos(usuario_id);

-- ==========================================================
--  TABLA: DETALLE_PEDIDO
-- ==========================================================
CREATE TABLE detalle_pedido (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pedido_id BIGINT NOT NULL,
  producto_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
  FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
-- TABLA: ENVIOS
-- ==========================================================
CREATE TABLE envios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pedido_id BIGINT NOT NULL,
  fecha_envio DATETIME,
  fecha_entrega_estimada DATE,
  fecha_entrega_real DATE,
  estado VARCHAR(50) DEFAULT 'EN_PREPARACION',
  direccion_entrega VARCHAR(255),
  observaciones TEXT,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  TABLA: RESENAS
-- ==========================================================
CREATE TABLE resenas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  producto_id BIGINT NOT NULL,
  calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
  comentario TEXT,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
  FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================================
--  ÍNDICES PARA OPTIMIZACIÓN
-- ==========================================================
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_productos_categoria ON productos(categoria_id);
CREATE INDEX idx_productos_activo ON productos(activo);
CREATE INDEX idx_categorias_activo ON categorias(activo);
CREATE INDEX idx_carritos_usuario ON carritos(usuario_id);
CREATE INDEX idx_carritos_activo ON carritos(activo);

--  DATOS 

INSERT INTO roles (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema'),
('CLIENTE', 'Usuario cliente');


INSERT INTO categorias (nombre, descripcion, activo) VALUES
('Frutas', 'Frutas frescas', TRUE),
('Verduras', 'Verduras orgánicas', TRUE);


INSERT INTO productos (codigo, nombre, descripcion, precio, stock, origen, categoria_id, activo) VALUES
('FR001', 'Manzanas', 'Manzanas frescas', 1200.00, 100, 'Maule', 1, TRUE),
('VR001', 'Zanahorias', 'Zanahorias orgánicas', 900.00, 80, 'O''Higgins', 2, TRUE);

-- 1 usuario Admin (password: admin123)
INSERT INTO usuarios (nombre, email, password, direccion, telefono, rol_id) VALUES
('Admin', 'admin@huertohogar.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Santiago', '+56911111111', 1);
