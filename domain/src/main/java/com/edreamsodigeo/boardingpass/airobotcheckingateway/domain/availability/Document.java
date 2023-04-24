package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability;

import java.util.List;
import java.util.Objects;

public class Document {
    private final DocumentType documentType;
    private final List<DocumentRequirement> documentRequirements;

    public Document(DocumentType documentType, List<DocumentRequirement> documentRequirements) {
        this.documentType = documentType;
        this.documentRequirements = documentRequirements;
    }

    public DocumentType documentType() {
        return documentType;
    }

    public List<DocumentRequirement> documentRequirements() {
        return documentRequirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return documentType == document.documentType && documentRequirements.equals(document.documentRequirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentType, documentRequirements);
    }

    @Override
    public String toString() {
        return "Document{"
                + "documentType=" + documentType
                + ", documentRequirements=" + documentRequirements
                + '}';
    }
}
