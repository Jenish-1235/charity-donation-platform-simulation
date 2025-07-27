-- Foreign Keys and Indexes

-- donor foreign keys
ALTER TABLE user_contact_preference ADD CONSTRAINT fk_ucp_donor FOREIGN KEY (donor_id) REFERENCES donor(id);
ALTER TABLE user_payment_preference ADD CONSTRAINT fk_upp_donor FOREIGN KEY (donor_id) REFERENCES donor(id);
ALTER TABLE campaign_transactions ADD CONSTRAINT fk_ct_donor FOREIGN KEY (donor_id) REFERENCES donor(id);
ALTER TABLE fundraiser_transactions ADD CONSTRAINT fk_ft_donor FOREIGN KEY (donor_id) REFERENCES donor(id);
ALTER TABLE recurring_donation ADD CONSTRAINT fk_rd_donor FOREIGN KEY (donor_id) REFERENCES donor(id);

-- charity foreign keys
ALTER TABLE campaign ADD CONSTRAINT fk_campaign_charity FOREIGN KEY (charity_id) REFERENCES charity(charity_id);
ALTER TABLE fundraiser ADD CONSTRAINT fk_fundraiser_charity FOREIGN KEY (charity_id) REFERENCES charity(charity_id);
ALTER TABLE recurring_donation ADD CONSTRAINT fk_rd_charity FOREIGN KEY (charity_id) REFERENCES charity(charity_id);

-- campaign & fundraiser transactions foreign keys
ALTER TABLE campaign_transactions ADD CONSTRAINT fk_ct_campaign FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);
ALTER TABLE fundraiser_transactions ADD CONSTRAINT fk_ft_fundraiser FOREIGN KEY (fundraiser_id) REFERENCES fundraiser(fundraiser_id);

-- campaign cache foreign key
ALTER TABLE campaign_cache ADD CONSTRAINT fk_cc_campaign FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);

-- fundraiser cache foreign key
ALTER TABLE fundraiser_cache ADD CONSTRAINT fk_fc_fundraiser FOREIGN KEY (fundraiser_id) REFERENCES fundraiser(fundraiser_id);

-- category relationships
ALTER TABLE campaign ADD CONSTRAINT fk_campaign_category FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE fundraiser ADD CONSTRAINT fk_fundraiser_category FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE charity ADD CONSTRAINT fk_charity_category FOREIGN KEY (category_id) REFERENCES charity_category(id);

-- recurring donation entity_type and payment method
ALTER TABLE recurring_donation ADD CONSTRAINT fk_rd_entity_type FOREIGN KEY (entity_type_id) REFERENCES entity_type(id);
ALTER TABLE recurring_donation ADD CONSTRAINT fk_rd_payment_method FOREIGN KEY (primary_payment_method_id) REFERENCES user_payment_preference(payment_id);

-- user contact and payment methods
ALTER TABLE user_contact_preference ADD CONSTRAINT fk_ucp_method FOREIGN KEY (method_id) REFERENCES contact_method_type(id);
ALTER TABLE user_payment_preference ADD CONSTRAINT fk_upp_method FOREIGN KEY (payment_method_id) REFERENCES payment_method_type(id);

-- Indexes for performance
CREATE INDEX idx_donor_income_range ON donor (income_range);
CREATE INDEX idx_donor_age ON donor (age);
CREATE INDEX idx_donor_gender ON donor (gender);
CREATE INDEX idx_ct_donor_id ON campaign_transactions (donor_id);
CREATE INDEX idx_ct_campaign_id ON campaign_transactions (campaign_id);
CREATE INDEX idx_ft_fundraiser_id ON fundraiser_transactions (fundraiser_id);
CREATE INDEX idx_campaign_charity_id ON campaign (charity_id);
CREATE INDEX idx_fundraiser_charity_id ON fundraiser (charity_id);
CREATE INDEX idx_rd_entity ON recurring_donation (entity_id, entity_type_id);
CREATE INDEX idx_rd_charity ON recurring_donation (charity_id);
CREATE INDEX idx_recurring_donor ON recurring_donation (donor_id);
CREATE INDEX idx_campaign_active ON campaign (is_active);
CREATE INDEX idx_fundraiser_active ON fundraiser (is_active);

CREATE INDEX idx_campaign_category ON campaign (category_id);
CREATE INDEX idx_charity_category ON charity (category_id);
CREATE INDEX idx_fundraiser_category ON fundraiser (category_id);

CREATE INDEX idx_donor_created_at ON donor (created_at);
CREATE INDEX idx_donor_last_donated_at ON donor (last_donated_at);


