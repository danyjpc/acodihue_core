


--Adds document name to table adm_document
ALTER TABLE adm_document
ADD COLUMN IF NOT EXISTS document_name VARCHAR(100) NOT NULL DEFAULT 'S/D';
COMMENT ON COLUMN adm_document.document_name IS 'nombre del documento';